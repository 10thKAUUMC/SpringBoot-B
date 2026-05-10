package umc.study.umc_mission.presentation.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 리뷰(Review) 요청 DTO 모음.
 *
 * <p>Member 도메인과 동일하게, 외부 클래스를 네임스페이스로 사용하고
 * 실제 데이터는 {@code static} 중첩 record로 둔다.</p>
 */
public class ReviewRequestDTO {

    /**
     * 리뷰 작성 요청 본문.
     *
     * <p>JWT 도입 전까지 작성자(memberId)는 Body로 받는다.
     * 평점은 1~5 정수만 허용하며, Bean Validation으로 1차 검증한다.</p>
     */
    @Schema(description = "리뷰 작성 요청")
    public record CreateReviewRequest(

            @Schema(description = "작성자 회원 PK", example = "1")
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,

            @Schema(description = "별점(1~5)", example = "5")
            @NotNull(message = "별점은 필수입니다.")
            @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
            @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
            Integer rating,

            @Schema(description = "리뷰 내용", example = "정말 맛있어요!")
            @Size(max = 1000, message = "리뷰 내용은 1000자 이하여야 합니다.")
            String content

    ) {
    }
}
