package com.example.umc10th.domain.review.dto;

public class ReviewReqDTO {

    public record CreateReviewRequest(
            Long storeId,
            Integer rating,
            String content,
            String photo
    ) {
    }
}
