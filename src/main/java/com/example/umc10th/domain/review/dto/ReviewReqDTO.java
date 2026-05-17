package com.example.umc10th.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ReviewReqDTO {

    public enum ReviewSortType {
        ID,
        RATING
    }

    @Schema(description = "리뷰 작성 요청")
    public record CreateReviewRequest(
            @Schema(description = "리뷰를 작성할 가게 ID", example = "15403567")
            @NotNull(message = "가게 ID는 필수입니다.")
            @Positive(message = "가게 ID는 양수여야 합니다.")
            Long storeId,
            @Schema(description = "별점", example = "5")
            @NotNull(message = "별점은 필수입니다.")
            @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
            @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
            Integer rating,
            @Schema(description = "리뷰 내용", example = "delicious")
            @NotBlank(message = "리뷰 내용은 필수입니다.")
            String content,
            @Schema(description = "사진 URL. 현재 저장 로직에서는 제외됩니다.", example = "null", nullable = true)
            String photo
    ) {
    }

    @Schema(description = "내가 작성한 리뷰 조회 요청")
    public record MyReviewCursorRequest(
            @Schema(description = "회원 ID", example = "1")
            @NotNull(message = "회원 ID는 필수입니다.")
            @Positive(message = "회원 ID는 양수여야 합니다.")
            Long memberId,

            @Schema(description = "정렬 기준. ID 또는 RATING", example = "ID", allowableValues = {"ID", "RATING"})
            ReviewSortType sort,

            @Schema(description = "마지막으로 조회한 리뷰 ID. 첫 조회 시 생략합니다.", example = "20")
            @Positive(message = "커서 리뷰 ID는 양수여야 합니다.")
            Long cursorId,

            @Schema(description = "별점순 조회 시 마지막으로 조회한 별점. 첫 조회 시 생략합니다.", example = "4.5")
            @DecimalMin(value = "1.0", message = "커서 별점은 1.0 이상이어야 합니다.")
            @DecimalMax(value = "5.0", message = "커서 별점은 5.0 이하여야 합니다.")
            BigDecimal cursorRating,

            @Schema(description = "한 번에 조회할 리뷰 개수", example = "10", defaultValue = "10")
            @Min(value = 1, message = "조회 개수는 1 이상이어야 합니다.")
            @Max(value = 50, message = "조회 개수는 50 이하여야 합니다.")
            Integer size
    ) {
    }
}
