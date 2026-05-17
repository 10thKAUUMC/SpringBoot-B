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
}
