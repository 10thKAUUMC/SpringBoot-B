package umc.study.umc_mission.presentation.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.study.umc_mission.domain.review.exception.ReviewSuccessCode;
import umc.study.umc_mission.domain.review.service.ReviewService;
import umc.study.umc_mission.global.apiPayload.ApiResponse;
import umc.study.umc_mission.presentation.review.dto.ReviewRequestDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO;

/**
 * 리뷰(Review) 도메인 REST 컨트롤러.
 *
 * <p>리뷰는 가게의 하위 자원이므로 URI를 {@code /api/v1/stores/{storeId}/reviews}로 잡았다.
 * RESTful 자원 모델링: 자원의 소속이 URI에 드러나도록 한다.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/{storeId}/reviews")
@Tag(name = "Review", description = "리뷰(Review) 도메인 API — 작성/조회")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 가게에 리뷰를 작성한다.
     *
     * <p>요청 예시:</p>
     * <pre>{@code
     * POST /api/v1/stores/10/reviews
     * { "memberId": 1, "rating": 5, "content": "정말 맛있어요!" }
     * }</pre>
     *
     * <p>응답 예시 (201 Created):</p>
     * <pre>{@code
     * {
     *   "isSuccess": true,
     *   "code": "REVIEW2010",
     *   "message": "성공적으로 리뷰를 작성했습니다.",
     *   "result": { "reviewId": 42, "memberId": 1, "storeId": 10, "createdAt": "2026-05-10T15:00:00" }
     * }
     * }</pre>
     */
    @PostMapping
    @Operation(summary = "리뷰 작성", description = "특정 가게에 회원이 리뷰를 작성한다.")
    public ApiResponse<ReviewResponseDTO.CreateReviewResponse> createReview(
            @PathVariable Long storeId,
            @Valid @RequestBody ReviewRequestDTO.CreateReviewRequest request
    ) {
        ReviewResponseDTO.CreateReviewResponse result = reviewService.createReview(storeId, request);
        return ApiResponse.onSuccess(ReviewSuccessCode.CREATE_REVIEW, result);
    }
}
