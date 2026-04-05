package umc.study.umc_mission.domain.review.repository;

import umc.study.umc_mission.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

/**
 * Review(리뷰) 도메인의 Repository 인터페이스.
 *
 * <p>이 인터페이스는 비즈니스 로직에서 필요한 메서드만 직접 정의한다.
 * 실제 DB 접근 방식(JPA, MyBatis 등)은 이 인터페이스를 구현하는
 * 구현체(Impl 클래스)에서 결정한다.</p>
 *
 * <p>회원이 작성한 가게 리뷰의 저장, 조회, 삭제 등을 처리한다.</p>
 *
 * <p>정의된 메서드:</p>
 * <ul>
 *   <li>{@link #save(Review)} — 리뷰를 저장하거나 수정</li>
 *   <li>{@link #findById(Long)} — 리뷰 ID로 단건 조회 (Optional 반환)</li>
 *   <li>{@link #findAll()} — 전체 리뷰를 List로 조회</li>
 *   <li>{@link #delete(Review)} — 리뷰 삭제</li>
 * </ul>
 */
public interface ReviewRepository {

    /**
     * 리뷰 엔티티를 저장한다.
     * 새로운 리뷰이면 INSERT, 이미 존재하면 UPDATE가 수행된다.
     *
     * @param review 저장할 리뷰 엔티티
     * @return 저장된 리뷰 엔티티 (ID가 채워진 상태)
     */
    Review save(Review review);

    /**
     * 리뷰 ID로 단건 조회한다.
     *
     * @param id 조회할 리뷰의 기본 키(PK)
     * @return 해당 ID의 리뷰가 존재하면 Optional.of(review), 없으면 Optional.empty()
     */
    Optional<Review> findById(Long id);

    /**
     * 전체 리뷰 목록을 조회한다.
     *
     * @return 모든 리뷰 엔티티의 리스트 (데이터가 없으면 빈 리스트)
     */
    List<Review> findAll();

    /**
     * 리뷰를 삭제한다.
     *
     * @param review 삭제할 리뷰 엔티티
     */
    void delete(Review review);
}
