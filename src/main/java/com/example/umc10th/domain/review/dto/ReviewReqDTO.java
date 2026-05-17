package com.example.umc10th.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ReviewReqDTO {

    @Schema(description = "리뷰 작성 요청")
    public record CreateReviewRequest(
            @Schema(description = "리뷰를 작성할 가게 ID", example = "15403567")
            Long storeId,
            @Schema(description = "별점", example = "5")
            Integer rating,
            @Schema(description = "리뷰 내용", example = "delicious")
            String content,
            @Schema(description = "사진 URL. 현재 저장 로직에서는 제외됩니다.", example = "null", nullable = true)
            String photo
    ) {
    }
}
