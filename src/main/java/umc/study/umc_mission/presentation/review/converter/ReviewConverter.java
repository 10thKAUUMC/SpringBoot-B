package umc.study.umc_mission.presentation.review.converter;

import org.springframework.data.domain.Slice;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.review.entity.Review;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.presentation.review.dto.ReviewRequestDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO.MyReviewSliceSort;

import java.util.List;

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

    /** 7주차 — 내 리뷰 한 행 변환. */
    public static ReviewResponseDTO.MyReviewItem toMyReviewItem(Review r) {
        return ReviewResponseDTO.MyReviewItem.builder()
                .reviewId(r.getId())
                .storeId(r.getStore().getId())
                .storeName(r.getStore().getName())
                .rating(r.getRating())
                .content(r.getContent())
                .createdAt(r.getCreatedAt())
                .build();
    }

    /**
     * 7주차 — Slice<Review>를 커서 응답 DTO로 변환한다.
     *
     * <p>다음 커서 계산:</p>
     * <ul>
     *   <li>hasNext == false → null</li>
     *   <li>ID 정렬 → {@code "ID:<lastId>"}</li>
     *   <li>STAR 정렬 → {@code "STAR:<lastRating>:<lastId>"} (별점 동률 대비 복합 커서)</li>
     * </ul>
     *
     * <p>커서 생성 책임을 컨버터에 두는 이유: 커서 포맷은 응답 스키마의 일부이고,
     * 응답 모양이 바뀌면 컨버터만 수정하면 되도록 위치를 한 곳에 모은다.</p>
     */
    public static ReviewResponseDTO.MyReviewSlice toMyReviewSlice(
            Slice<Review> slice, MyReviewSliceSort sort) {

        List<ReviewResponseDTO.MyReviewItem> items = slice.getContent().stream()
                .map(ReviewConverter::toMyReviewItem)
                .toList();

        String nextCursor = null;
        if (slice.hasNext() && !items.isEmpty()) {
            ReviewResponseDTO.MyReviewItem last = items.get(items.size() - 1);
            nextCursor = switch (sort) {
                case ID -> "ID:" + last.reviewId();
                case STAR -> "STAR:" + last.rating() + ":" + last.reviewId();
            };
        }

        return ReviewResponseDTO.MyReviewSlice.builder()
                .reviews(items)
                .size(items.size())
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
