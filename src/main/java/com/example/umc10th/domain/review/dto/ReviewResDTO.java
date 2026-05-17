package com.example.umc10th.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Schema(description = "내가 작성한 리뷰 커서 목록 응답")
    public record MyReviewCursorResponse(
            @Schema(description = "리뷰 목록")
            List<MyReviewSummaryResponse> reviews,
            @Schema(description = "정렬 기준", example = "ID")
            String sort,
            @Schema(description = "다음 페이지 조회에 사용할 리뷰 ID 커서", example = "10", nullable = true)
            Long nextCursorId,
            @Schema(description = "별점순 다음 페이지 조회에 사용할 별점 커서", example = "4.5", nullable = true)
            BigDecimal nextCursorRating,
            @Schema(description = "다음 페이지 존재 여부", example = "true")
            Boolean hasNext
    ) {
    }

    @Schema(description = "내가 작성한 리뷰 요약 응답")
    public record MyReviewSummaryResponse(
            @Schema(description = "리뷰 ID", example = "1")
            Long reviewId,
            @Schema(description = "가게 ID", example = "15403567")
            Long storeId,
            @Schema(description = "가게 이름", example = "반이학생마라탕")
            String storeName,
            @Schema(description = "작성자 닉네임", example = "닉네임1234")
            String memberName,
            @Schema(description = "별점", example = "4.5")
            BigDecimal rating,
            @Schema(description = "리뷰 내용", example = "음 너무 맛있어요.")
            String content,
            @Schema(description = "리뷰 작성일시", example = "2026-05-18T02:09:00")
            LocalDateTime createdAt
    ) {
    }
}
