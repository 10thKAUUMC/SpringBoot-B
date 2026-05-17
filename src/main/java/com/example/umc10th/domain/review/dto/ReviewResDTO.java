package com.example.umc10th.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ReviewResDTO {

    @Schema(description = "리뷰 작성 응답")
    public record CreateReviewResponse(
            @Schema(description = "생성된 리뷰 ID", example = "1")
            Long reviewId,
            @Schema(description = "가게 ID", example = "15403567")
            Long storeId,
            @Schema(description = "별점", example = "5")
            Integer rating,
            @Schema(description = "리뷰 내용", example = "delicious")
            String content
    ) {
    }
}
