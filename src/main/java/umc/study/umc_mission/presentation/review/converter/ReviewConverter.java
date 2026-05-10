package umc.study.umc_mission.presentation.review.converter;

import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.review.entity.Review;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.presentation.review.dto.ReviewRequestDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO;

/**
 * Review 엔티티 ↔ Review DTO 변환기.
 *
 * <p>요청 DTO를 엔티티로(toEntity), 엔티티를 응답 DTO로(toCreateResponse) 변환하는 정적 유틸.
 * MemberConverter와 동일한 이유로 별도 파일 + 정적 메서드 묶음으로 둔다.</p>
 */
public final class ReviewConverter {

    private ReviewConverter() {
        throw new UnsupportedOperationException("정적 유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    /**
     * 요청 DTO와 도메인 객체(Member, Store)를 받아 새 Review 엔티티를 만든다.
     *
     * <p>Review 엔티티는 rating을 String(length=1)으로 저장하므로 Integer→String 변환이 필요하다.
     * 변환 위치는 컨버터로 한정해, 도메인 엔티티는 자기 형식만 알면 되도록 한다.</p>
     */
    public static Review toEntity(ReviewRequestDTO.CreateReviewRequest request, Member member, Store store) {
        return Review.builder()
                .member(member)
                .store(store)
                .rating(String.valueOf(request.rating()))
                .content(request.content())
                .build();
    }

    /** 저장된 Review 엔티티를 작성 응답 DTO로 변환한다. */
    public static ReviewResponseDTO.CreateReviewResponse toCreateResponse(Review review) {
        return ReviewResponseDTO.CreateReviewResponse.builder()
                .reviewId(review.getId())
                .memberId(review.getMember().getId())
                .storeId(review.getStore().getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
