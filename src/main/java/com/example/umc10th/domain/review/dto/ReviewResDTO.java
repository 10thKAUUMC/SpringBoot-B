package com.example.umc10th.domain.review.dto;

public class ReviewResDTO {

    public record CreateReviewResponse(
            Long reviewId,
            Long storeId,
            Integer rating,
            String content,
            String photo
    ) {
    }
}
