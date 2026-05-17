package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.global.util.AuthMemberResolver;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository,
                         StoreRepository storeRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.storeRepository = storeRepository;
    }

    public ReviewResDTO.MyReviewCursorResponse getMyReviews(
            ReviewReqDTO.MyReviewCursorRequest request
    ) {
        if (!memberRepository.existsById(request.memberId())) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        ReviewReqDTO.ReviewSortType sort = request.sort() == null ? ReviewReqDTO.ReviewSortType.ID : request.sort();
        int size = request.size() == null ? 10 : request.size();
        Slice<Review> reviewSlice = switch (sort) {
            case ID -> reviewRepository.findMyReviewsByIdCursor(
                    request.memberId(),
                    request.cursorId(),
                    PageRequest.of(0, size)
            );
            case RATING -> reviewRepository.findMyReviewsByRatingCursor(
                    request.memberId(),
                    request.cursorRating(),
                    request.cursorId(),
                    PageRequest.of(0, size)
            );
        };

        List<ReviewResDTO.MyReviewSummaryResponse> reviews = reviewSlice.getContent().stream()
                .map(this::toMyReviewSummaryResponse)
                .toList();
        Review lastReview = reviews.isEmpty() ? null : reviewSlice.getContent().get(reviewSlice.getContent().size() - 1);

        return new ReviewResDTO.MyReviewCursorResponse(
                reviews,
                sort.name(),
                reviewSlice.hasNext() && lastReview != null ? lastReview.getId() : null,
                reviewSlice.hasNext() && lastReview != null && sort == ReviewReqDTO.ReviewSortType.RATING
                        ? lastReview.getScore()
                        : null,
                reviewSlice.hasNext()
        );
    }

    @Transactional
    public ReviewResDTO.CreateReviewResponse createReview(
            String authorization,
            ReviewReqDTO.CreateReviewRequest request
    ) {
        Member member = memberRepository.findById(AuthMemberResolver.resolveMemberId(authorization))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.STORE_NOT_FOUND));

        Review review = new Review(
                member,
                store,
                BigDecimal.valueOf(request.rating()),
                request.content()
        );
        Review savedReview = reviewRepository.save(review);

        return new ReviewResDTO.CreateReviewResponse(
                savedReview.getId(),
                savedReview.getStore().getId(),
                savedReview.getScore().intValue(),
                savedReview.getContent()
        );
    }

    private ReviewResDTO.MyReviewSummaryResponse toMyReviewSummaryResponse(Review review) {
        return new ReviewResDTO.MyReviewSummaryResponse(
                review.getId(),
                review.getStore().getId(),
                review.getStore().getName(),
                review.getMember().getName(),
                review.getScore(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
