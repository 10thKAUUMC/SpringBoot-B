package umc.study.umc_mission.domain.review.service;

import umc.study.umc_mission.presentation.review.dto.ReviewRequestDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO.MyReviewSliceSort;

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

    /**
     * 7주차 추가 — 내가 작성한 리뷰 목록을 커서 기반 페이지네이션으로 조회.
     *
     * @param memberId 작성자 회원 PK
     * @param sort     정렬 기준 (ID DESC | STAR DESC)
     * @param cursor   "ID:42" 또는 "STAR:4:42" 형식의 다음 커서. null/-1이면 처음.
     * @param size     한 페이지에 가져올 개수
     */
    ReviewResponseDTO.MyReviewSlice getMyReviews(Long memberId, MyReviewSliceSort sort, String cursor, int size);
}
