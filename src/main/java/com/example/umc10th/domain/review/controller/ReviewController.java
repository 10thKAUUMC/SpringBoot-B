package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.status.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "리뷰 작성 API")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/my")
    @Operation(
            summary = "내가 생성한 리뷰 목록 조회",
            description = "Request Body로 받은 회원 ID 기준 작성 리뷰를 커서 기반으로 조회합니다. 사진 정보는 응답에서 제외하며 ID순/별점순 정렬을 지원합니다."
    )
    public ApiResponse<ReviewResDTO.MyReviewCursorResponse> getMyReviews(
            @Valid @RequestBody ReviewReqDTO.MyReviewCursorRequest request
    ) {
        ReviewResDTO.MyReviewCursorResponse response = reviewService.getMyReviews(request);

        return ApiResponse.onSuccess(response);
    }

    @PostMapping
    @Operation(
            summary = "리뷰 작성",
            description = "마이페이지에서 가게 리뷰를 작성합니다. 사진 저장은 현재 구현 범위에서 제외했습니다."
    )
    public ResponseEntity<ApiResponse<ReviewResDTO.CreateReviewResponse>> createReview(
            @Parameter(description = "Bearer access token. 임시 구현에서는 Bearer 뒤 숫자를 회원 ID로 사용합니다.", example = "Bearer 1")
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody ReviewReqDTO.CreateReviewRequest request
    ) {
        ReviewResDTO.CreateReviewResponse response = reviewService.createReview(authorization, request);

        return ResponseEntity.status(GeneralSuccessCode.CREATED.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }
}
