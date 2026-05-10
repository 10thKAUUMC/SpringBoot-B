package umc.study.umc_mission.domain.point.repository;

import umc.study.umc_mission.domain.point.entity.PointHistory;

import java.util.List;
import java.util.Optional;

/**
 * PointHistory(포인트 내역) 도메인의 Repository 인터페이스.
 *
 * <p>이 인터페이스는 비즈니스 로직에서 필요한 메서드만 직접 정의한다.
 * 실제 DB 접근 방식(JPA, MyBatis 등)은 이 인터페이스를 구현하는
 * 구현체(Impl 클래스)에서 결정한다.</p>
 *
 * <p>회원의 포인트 적립/사용 내역을 기록하고 조회하는 데 사용된다.</p>
 *
 * <p>정의된 메서드:</p>
 * <ul>
 *   <li>{@link #save(PointHistory)} — 포인트 내역을 저장하거나 수정</li>
 *   <li>{@link #findById(Long)} — 포인트 내역 ID로 단건 조회 (Optional 반환)</li>
 *   <li>{@link #findAll()} — 전체 포인트 내역을 List로 조회</li>
 *   <li>{@link #delete(PointHistory)} — 포인트 내역 삭제</li>
 * </ul>
 */
public interface PointHistoryRepository {

    /**
     * 포인트 내역 엔티티를 저장한다.
     * 새로운 내역이면 INSERT, 이미 존재하면 UPDATE가 수행된다.
     *
     * @param pointHistory 저장할 포인트 내역 엔티티
     * @return 저장된 포인트 내역 엔티티 (ID가 채워진 상태)
     */
    PointHistory save(PointHistory pointHistory);

    /**
     * 포인트 내역 ID로 단건 조회한다.
     *
     * @param id 조회할 포인트 내역의 기본 키(PK)
     * @return 해당 ID의 포인트 내역이 존재하면 Optional.of(pointHistory), 없으면 Optional.empty()
     */
    Optional<PointHistory> findById(Long id);

    /**
     * 전체 포인트 내역 목록을 조회한다.
     *
     * @return 모든 포인트 내역 엔티티의 리스트 (데이터가 없으면 빈 리스트)
     */
    List<PointHistory> findAll();

    /**
     * 포인트 내역을 삭제한다.
     *
     * @param pointHistory 삭제할 포인트 내역 엔티티
     */
    void delete(PointHistory pointHistory);
}
