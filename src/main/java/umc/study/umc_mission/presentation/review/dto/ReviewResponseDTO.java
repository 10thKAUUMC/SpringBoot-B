package umc.study.umc_mission.presentation.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 리뷰(Review) 응답 DTO 모음.
 *
 * <p>엔티티를 직접 노출하지 않고 평면화된 record로 반환한다.</p>
 */
public class ReviewResponseDTO {

    /**
     * 리뷰 작성 완료 응답.
     *
     * <p>식별자(reviewId)와 작성 시각만 돌려주는 가벼운 응답.
     * 전체 리뷰 내용은 클라이언트가 이미 보낸 값이므로 다시 내려보내지 않는다(트래픽 절약).</p>
     */
    @Builder
    @Schema(description = "리뷰 작성 응답")
    public record CreateReviewResponse(

            @Schema(description = "생성된 리뷰의 PK", example = "42")
            Long reviewId,

            @Schema(description = "작성자 회원 PK", example = "1")
            Long memberId,

            @Schema(description = "리뷰 대상 가게 PK", example = "10")
            Long storeId,

            @Schema(description = "작성 시각")
            LocalDateTime createdAt

    ) {
    }

    /**
     * 7주차 — 내 리뷰 목록 정렬 기준.
     *
     * <p>ID(작성 순) / STAR(별점 순) 두 가지를 지원한다. enum으로 두면 컨트롤러에서 Spring이
     * 문자열 → enum 자동 변환 + 잘못된 값에 400을 자동 반환해주므로 검증 코드가 줄어든다.</p>
     */
    public enum MyReviewSliceSort {
        ID, STAR
    }

    /** 7주차 — 내 리뷰 목록의 한 행. */
    @Builder
    @Schema(description = "내 리뷰 목록의 한 행")
    public record MyReviewItem(

            @Schema(description = "Review PK", example = "42")
            Long reviewId,

            @Schema(description = "리뷰 대상 가게 PK", example = "10")
            Long storeId,

            @Schema(description = "리뷰 대상 가게 이름", example = "맛있는 김치찌개")
            String storeName,

            @Schema(description = "별점 (1~5)", example = "5")
            String rating,

            @Schema(description = "리뷰 내용")
            String content,

            @Schema(description = "작성 시각")
            LocalDateTime createdAt

    ) {
    }

    /**
     * 7주차 — 내 리뷰 목록 커서 페이지 응답.
     *
     * <p>Slice를 평면화한 형태. 오프셋과 달리 totalElements/totalPages가 없고,
     * <b>hasNext + nextCursor</b>만 노출한다 (커서 페이징의 본질).</p>
     */
    @Builder
    @Schema(description = "내 리뷰 목록 커서 페이지 응답")
    public record MyReviewSlice(

            List<MyReviewItem> reviews,

            @Schema(description = "이번 응답에 담긴 행 개수", example = "10")
            Integer size,

            @Schema(description = "다음 페이지 존재 여부")
            Boolean hasNext,

            @Schema(description = "다음 페이지 요청에 그대로 넘길 커서. " +
                    "ID 정렬: \"ID:<lastReviewId>\". STAR 정렬: \"STAR:<lastRating>:<lastReviewId>\". " +
                    "hasNext가 false이면 null.",
                    example = "ID:35")
            String nextCursor

    ) {
    }
}
