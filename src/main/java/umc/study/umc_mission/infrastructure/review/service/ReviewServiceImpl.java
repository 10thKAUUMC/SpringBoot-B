package umc.study.umc_mission.infrastructure.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
import umc.study.umc_mission.presentation.review.dto.ReviewResponseDTO.MyReviewSliceSort;

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

    /**
     * 7주차 — 내 리뷰 목록 (커서 페이지네이션).
     *
     * <p>흐름:</p>
     * <ol>
     *   <li>회원 존재 확인 (부재 시 명확한 4xx로 실패시킴 — 빈 페이지를 돌려보내면 잘못된 ID를 못 알아챔)</li>
     *   <li>커서 문자열 파싱 — 컨트롤러가 아닌 서비스에서 처리 (도메인 규칙에 가까움)</li>
     *   <li>정렬 기준에 따라 다른 리포지토리 메서드 호출</li>
     *   <li>Slice → 응답 DTO 평면화는 컨버터에 위임</li>
     * </ol>
     */
    @Override
    public ReviewResponseDTO.MyReviewSlice getMyReviews(
            Long memberId, MyReviewSliceSort sort, String cursor, int size) {

        if (memberRepository.findById(memberId).isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(0, size);

        Slice<Review> slice = switch (sort) {
            case ID -> reviewRepository.findMyReviewsByIdCursor(memberId, parseIdCursor(cursor), pageable);
            case STAR -> {
                StarCursor sc = parseStarCursor(cursor);
                yield reviewRepository.findMyReviewsByStarCursor(memberId, sc.rating(), sc.id(), pageable);
            }
        };

        return ReviewConverter.toMyReviewSlice(slice, sort);
    }

    /**
     * ID 커서 파싱.
     * <p>허용 입력: null, 빈 문자열, "-1"(워크북 컨벤션: 처음부터 조회), "ID:42", "42".
     * 파싱 실패 시 첫 페이지로 간주(null).</p>
     */
    private Long parseIdCursor(String cursor) {
        if (cursor == null || cursor.isBlank() || "-1".equals(cursor)) return null;
        String raw = cursor.startsWith("ID:") ? cursor.substring(3) : cursor;
        try {
            long v = Long.parseLong(raw);
            return v < 0 ? null : v;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    /**
     * STAR 커서 파싱: "STAR:<rating>:<reviewId>".
     * <p>둘 다 null이면 첫 페이지로 간주.</p>
     */
    private StarCursor parseStarCursor(String cursor) {
        if (cursor == null || cursor.isBlank() || "-1".equals(cursor)) {
            return new StarCursor(null, null);
        }
        String body = cursor.startsWith("STAR:") ? cursor.substring(5) : cursor;
        String[] parts = body.split(":");
        if (parts.length != 2) return new StarCursor(null, null);
        try {
            return new StarCursor(parts[0], Long.parseLong(parts[1]));
        } catch (NumberFormatException ignored) {
            return new StarCursor(null, null);
        }
    }

    /** 별점 복합 커서 파싱 결과 (rating, id). */
    private record StarCursor(String rating, Long id) {}
}
