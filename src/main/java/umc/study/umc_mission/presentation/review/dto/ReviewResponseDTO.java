package umc.study.umc_mission.presentation.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

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
}
