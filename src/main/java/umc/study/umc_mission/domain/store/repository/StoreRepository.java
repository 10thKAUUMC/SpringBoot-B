package umc.study.umc_mission.domain.store.repository;

import umc.study.umc_mission.domain.store.entity.Store;

import java.util.List;
import java.util.Optional;

/**
 * Store(가게) 도메인의 Repository 인터페이스.
 *
 * <p>이 인터페이스는 비즈니스 로직에서 필요한 메서드만 직접 정의한다.
 * 실제 DB 접근 방식(JPA, MyBatis 등)은 이 인터페이스를 구현하는
 * 구현체(Impl 클래스)에서 결정한다.</p>
 *
 * <p>이 패턴의 장점: 도메인 계층이 특정 기술(JPA 등)에 의존하지 않으므로,
 * 나중에 데이터베이스 접근 기술을 변경하더라도 도메인 코드를 수정할 필요가 없다.</p>
 *
 * <p>정의된 메서드:</p>
 * <ul>
 *   <li>{@link #save(Store)} — 가게 정보를 저장하거나 수정</li>
 *   <li>{@link #findById(Long)} — 가게 ID로 단건 조회 (Optional 반환)</li>
 *   <li>{@link #findAll()} — 전체 가게를 List로 조회</li>
 *   <li>{@link #delete(Store)} — 가게 삭제</li>
 * </ul>
 */
public interface StoreRepository {

    /**
     * 가게 엔티티를 저장한다.
     * 새로운 가게이면 INSERT, 이미 존재하면 UPDATE가 수행된다.
     *
     * @param store 저장할 가게 엔티티
     * @return 저장된 가게 엔티티 (ID가 채워진 상태)
     */
    Store save(Store store);

    /**
     * 가게 ID로 단건 조회한다.
     *
     * @param id 조회할 가게의 기본 키(PK)
     * @return 해당 ID의 가게가 존재하면 Optional.of(store), 없으면 Optional.empty()
     */
    Optional<Store> findById(Long id);

    /**
     * 전체 가게 목록을 조회한다.
     *
     * @return 모든 가게 엔티티의 리스트 (데이터가 없으면 빈 리스트)
     */
    List<Store> findAll();

    /**
     * 가게를 삭제한다.
     *
     * @param store 삭제할 가게 엔티티
     */
    void delete(Store store);
}
