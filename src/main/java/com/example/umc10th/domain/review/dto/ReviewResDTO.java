package com.example.umc10th.domain.review.dto;

import java.util.List;

public class ReviewResDTO {
    public record CreateReviewResultDTO (
        Long reviewId
    ) {}

    public record MyReviewDTO(
            Long reviewId,
            String storeName,
            String content,
            Float rating
    ) {}

    public record MyReviewListDTO(
            List<MyReviewDTO> reviewList,
            Integer listSize,
            Boolean hasNext,
            Long nextCursorId,
            Float nextCursorRating
    ) {}
}
