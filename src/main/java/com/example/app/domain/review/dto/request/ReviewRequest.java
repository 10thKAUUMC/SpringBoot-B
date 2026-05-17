package com.example.app.domain.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewRequest {

    @NotNull(message = "가게 ID는 필수입니다.")
    private Long storeId;

    @NotNull(message = "별점은 필수입니다.")
    @Min(value = 1, message = "별점은 1 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 5 이하여야 합니다.")
    private Integer rating;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    @Size(min = 10, max = 500, message = "리뷰는 10자 이상 500자 이하로 작성해주세요.")
    private String content;

    private List<String> imageUrls;
}
