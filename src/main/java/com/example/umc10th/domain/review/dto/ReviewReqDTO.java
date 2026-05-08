package com.example.umc10th.domain.review.dto;

import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class CreateReviewDTO {
        private String content;
        private Float rating;
    }
}
