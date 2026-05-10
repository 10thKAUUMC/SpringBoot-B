package umc.study.umc_mission.domain.review.service;

import umc.study.umc_mission.presentation.review.dto.ReviewRequestDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO;

/**
 * Review(리뷰) 도메인 서비스 인터페이스.
 *
 * <p>리뷰 작성 등의 비즈니스 로직을 정의하는 포트(port)이다.
 * 구현체는 {@code infrastructure/review/service/ReviewServiceImpl}.</p>
 */
public interface ReviewService {

    /**
     * 가게에 대한 리뷰를 작성한다.
     *
     * @param storeId 리뷰 대상 가게의 PK
     * @param request 작성 요청 (회원 ID, 평점, 내용)
     * @return 생성된 리뷰의 응답 DTO
     */
    ReviewResponseDTO.CreateReviewResponse createReview(Long storeId, ReviewRequestDTO.CreateReviewRequest request);
}
