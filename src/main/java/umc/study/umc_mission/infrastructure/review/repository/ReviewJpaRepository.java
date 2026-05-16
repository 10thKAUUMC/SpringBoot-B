package umc.study.umc_mission.infrastructure.review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.study.umc_mission.domain.review.entity.Review;

/**
 * Spring Data JPA가 자동으로 구현체를 생성해주는 인터페이스.
 * ReviewRepositoryImpl 내부에서만 사용된다.
 *
 * <p>7주차 추가: 내 리뷰 조회를 위한 <b>커서 기반</b> 페이지네이션 JPQL.</p>
 *
 * <p>왜 Slice인가:</p>
 * <ul>
 *   <li>커서 페이지네이션은 "다음 데이터 존재 여부"만 알면 충분하다(전체 카운트는 의미 X).</li>
 *   <li>Slice는 size+1 트릭으로 hasNext만 계산하므로 카운트 쿼리(=풀스캔에 가까운 COUNT)가
 *       발행되지 않아 Page보다 가볍다.</li>
 * </ul>
 */
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    /**
     * ID 기준 커서 페이지네이션 (정렬: id DESC).
     *
     * <p>{@code (:cursorId IS NULL OR r.id < :cursorId)}로 첫 페이지/다음 페이지를 한 쿼리로 표현.
     * Hibernate가 IS NULL 분기를 알아서 처리하므로, 자바 쪽에서 분기해 두 메서드를 만들지 않아도 된다.</p>
     */
    @Query(
            "select r from Review r " +
                    "where r.member.id = :memberId " +
                    "  and (:cursorId is null or r.id < :cursorId) " +
                    "order by r.id desc"
    )
    Slice<Review> findMyReviewsByIdCursor(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable);

    /**
     * 별점 기준 커서 페이지네이션 (정렬: rating DESC, id DESC).
     *
     * <p>별점은 중복이 흔하므로 1주차의 커서 비교 패턴
     * {@code (rating &lt; cursorRating) OR (rating = cursorRating AND id &lt; cursorId)}을 그대로 적용.
     * 처음 호출(커서 둘 다 null)은 전체 정렬 조건만 적용한다.</p>
     *
     * <p>주의: 현재 Review.rating은 String("1"~"5")이라 사전순 비교가 숫자 비교와 일치한다.
     * 추후 컬럼이 Integer로 마이그레이션되면 파라미터 타입과 비교만 바꾸면 된다.</p>
     */
    @Query(
            "select r from Review r " +
                    "where r.member.id = :memberId " +
                    "  and (" +
                    "        :cursorRating is null " +
                    "        or r.rating < :cursorRating " +
                    "        or (r.rating = :cursorRating and r.id < :cursorId)" +
                    "      ) " +
                    "order by r.rating desc, r.id desc"
    )
    Slice<Review> findMyReviewsByStarCursor(
            @Param("memberId") Long memberId,
            @Param("cursorRating") String cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable);
}
