package umc.study.umc_mission.infrastructure.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.member.exception.MemberErrorCode;
import umc.study.umc_mission.domain.member.exception.MemberException;
import umc.study.umc_mission.domain.member.repository.MemberRepository;
import umc.study.umc_mission.domain.review.entity.Review;
import umc.study.umc_mission.domain.review.repository.ReviewRepository;
import umc.study.umc_mission.domain.review.service.ReviewService;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.domain.store.exception.StoreErrorCode;
import umc.study.umc_mission.domain.store.exception.StoreException;
import umc.study.umc_mission.domain.store.repository.StoreRepository;
import umc.study.umc_mission.presentation.review.converter.ReviewConverter;
import umc.study.umc_mission.presentation.review.dto.ReviewRequestDTO;
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO;

/**
 * {@link ReviewService}의 JPA 기반 구현체.
 *
 * <h3>왜 클래스 레벨에 readOnly = true를 두고, 쓰기 메서드에 별도 @Transactional을 다는가?</h3>
 * <p>대부분의 조회 메서드는 readOnly=true가 적절하므로 클래스 디폴트로 둔다.
 * 단, 리뷰 작성처럼 INSERT가 있는 메서드는 readOnly를 끄기 위해 메서드 단에서 다시
 * {@code @Transactional}을 선언한다. (Spring 트랜잭션은 가까운 어노테이션이 우선.)</p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    /**
     * 리뷰 작성.
     *
     * <p>흐름:</p>
     * <ol>
     *   <li>회원/가게 존재 확인 — 부재 시 도메인 예외 던짐.</li>
     *   <li>요청 DTO + 도메인 객체로 새 Review 엔티티 생성 (컨버터 위임).</li>
     *   <li>저장 후 응답 DTO로 변환해 반환.</li>
     * </ol>
     *
     * <p>@Valid가 컨트롤러에서 평점 1~5 범위를 1차 보장하므로, 서비스에서 추가 검증은 생략한다.
     * (방어적 검증이 필요하면 ReviewErrorCode.INVALID_RATING으로 던질 수 있다.)</p>
     */
    @Override
    @Transactional
    public ReviewResponseDTO.CreateReviewResponse createReview(
            Long storeId, ReviewRequestDTO.CreateReviewRequest request) {

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

        Review review = ReviewConverter.toEntity(request, member, store);
        Review saved = reviewRepository.save(review);

        return ReviewConverter.toCreateResponse(saved);
    }
}
