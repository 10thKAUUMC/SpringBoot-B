package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.status.GeneralSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResDTO.CreateReviewResponse>> createReview(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ReviewReqDTO.CreateReviewRequest request
    ) {
        ReviewResDTO.CreateReviewResponse response = new ReviewResDTO.CreateReviewResponse(
                null,
                request.storeId(),
                request.rating(),
                request.content(),
                request.photo()
        );

        return ResponseEntity.status(GeneralSuccessCode.CREATED.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }
}
