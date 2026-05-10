package com.example.app.domain.review.dto.response;

import com.example.app.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReviewResponse {
    private Long reviewId;
    private Long userId;
    private String nickname;
    private Long storeId;
    private String storeName;
    private Integer rating;
    private String content;
    private List<String> imageUrls;
    private LocalDateTime createdAt;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .userId(review.getUser().getUserId())
                .nickname(review.getUser().getNickname())
                .storeId(review.getStore().getStoreId())
                .storeName(review.getStore().getName())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrls(review.getImages().stream()
                        .map(img -> img.getImageUrl())
                        .collect(Collectors.toList()))
                .createdAt(review.getCreatedAt())
                .build();
    }
}
