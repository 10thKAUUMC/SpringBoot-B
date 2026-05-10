package com.example.app.domain.review.controller;

import com.example.app.domain.review.dto.request.ReviewRequest;
import com.example.app.domain.review.dto.response.ReviewResponse;
import com.example.app.domain.review.service.ReviewService;
import com.example.app.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewResponse> createReview(
            @RequestParam Long userId,
            @Valid @RequestBody ReviewRequest request) {
        return ApiResponse.ok("리뷰가 작성되었습니다.", reviewService.createReview(userId, request));
    }

    @Operation(summary = "가게별 리뷰 목록 조회")
    @GetMapping("/store/{storeId}")
    public ApiResponse<Page<ReviewResponse>> getStoreReviews(
            @PathVariable Long storeId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.ok(reviewService.getStoreReviews(storeId, pageable));
    }

    @Operation(summary = "내 리뷰 목록 조회")
    @GetMapping("/my")
    public ApiResponse<Page<ReviewResponse>> getMyReviews(
            @RequestParam Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.ok(reviewService.getUserReviews(userId, pageable));
    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long userId) {
        reviewService.deleteReview(userId, reviewId);
        return ApiResponse.ok();
    }
}
