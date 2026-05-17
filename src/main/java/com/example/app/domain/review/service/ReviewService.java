package com.example.app.domain.review.service;

import com.example.app.domain.review.dto.request.ReviewRequest;
import com.example.app.domain.review.dto.response.ReviewResponse;
import com.example.app.domain.review.entity.Review;
import com.example.app.domain.review.entity.ReviewImage;
import com.example.app.domain.review.repository.ReviewRepository;
import com.example.app.domain.store.entity.Store;
import com.example.app.domain.store.service.StoreService;
import com.example.app.domain.user.entity.User;
import com.example.app.domain.user.repository.UserRepository;
import com.example.app.global.exception.BusinessException;
import com.example.app.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreService storeService;

    @Transactional
    public ReviewResponse createReview(Long userId, ReviewRequest request) {
        if (reviewRepository.existsByUser_UserIdAndStore_StoreId(userId, request.getStoreId())) {
            throw new BusinessException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Store store = storeService.getStoreEntity(request.getStoreId());

        Review review = Review.builder()
                .user(user)
                .store(store)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        if (request.getImageUrls() != null) {
            List<ReviewImage> images = request.getImageUrls().stream()
                    .map(url -> ReviewImage.builder()
                            .review(review)
                            .imageUrl(url)
                            .build())
                    .collect(Collectors.toList());
            review.getImages().addAll(images);
        }

        return ReviewResponse.from(reviewRepository.save(review));
    }

    public Page<ReviewResponse> getStoreReviews(Long storeId, Pageable pageable) {
        return reviewRepository.findByStore_StoreId(storeId, pageable).map(ReviewResponse::from);
    }

    public Page<ReviewResponse> getUserReviews(Long userId, Pageable pageable) {
        return reviewRepository.findByUser_UserId(userId, pageable).map(ReviewResponse::from);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        reviewRepository.delete(review);
    }
}
