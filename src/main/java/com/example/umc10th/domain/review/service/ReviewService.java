package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewResDTO.CreateReviewResultDTO createReview(
            Long userId,
            Long storeId,
            ReviewReqDTO.CreateReviewDTO request
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        Review review = Review.builder()
                .rating(request.rating())
                .content(request.content())
                .user(user)
                .store(store)
                .build();

        Review savedReview = reviewRepository.save(review);

        return new ReviewResDTO.CreateReviewResultDTO(
                savedReview.getReviewId()
        );

    }

    public ReviewResDTO.MyReviewListDTO getMyReviews(
            Long userId,
            Long cursorId,
            Float cursorRating,
            Integer size,
            String sort
    ) {
        boolean isRatingSort = sort.equals("rating");

        List<Review> reviews;

        if (isRatingSort) {
            reviews = reviewRepository.findMyReviewsOrderByRating(
                    userId,
                    cursorRating,
                    cursorId,
                    PageRequest.of(0, size + 1)
            );
        } else {
            reviews = reviewRepository.findMyReviewsOrderById(
                    userId,
                    cursorId,
                    PageRequest.of(0, size + 1)
            );
        }

        return ReviewConverter.toMyReviewListDTO(reviews, size, isRatingSort);
    }

}
