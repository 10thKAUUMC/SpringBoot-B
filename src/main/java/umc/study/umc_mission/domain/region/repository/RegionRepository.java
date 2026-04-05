package umc.study.umc_mission.domain.region.repository;

import umc.study.umc_mission.domain.region.entity.Region;

import java.util.List;
import java.util.Optional;

/**
 * Region(지역) 도메인의 Repository 인터페이스.
 *
 * <p>이 인터페이스는 비즈니스 로직에서 필요한 메서드만 직접 정의한다.
 * 실제 DB 접근 방식(JPA, MyBatis 등)은 이 인터페이스를 구현하는
 * 구현체(Impl 클래스)에서 결정한다.</p>
 *
 * <p>지역 정보(예: 서울, 부산 등)의 저장, 조회, 삭제 등을 처리한다.
 * 가게(Store)가 어느 지역에 속하는지를 연결할 때 사용된다.</p>
 *
 * <p>정의된 메서드:</p>
 * <ul>
 *   <li>{@link #save(Region)} — 지역 정보를 저장하거나 수정</li>
 *   <li>{@link #findById(Long)} — 지역 ID로 단건 조회 (Optional 반환)</li>
 *   <li>{@link #findAll()} — 전체 지역을 List로 조회</li>
 *   <li>{@link #delete(Region)} — 지역 삭제</li>
 * </ul>
 */
public interface RegionRepository {

    /**
     * 지역 엔티티를 저장한다.
     * 새로운 지역이면 INSERT, 이미 존재하면 UPDATE가 수행된다.
     *
     * @param region 저장할 지역 엔티티
     * @return 저장된 지역 엔티티 (ID가 채워진 상태)
     */
    Region save(Region region);

    /**
     * 지역 ID로 단건 조회한다.
     *
     * @param id 조회할 지역의 기본 키(PK)
     * @return 해당 ID의 지역이 존재하면 Optional.of(region), 없으면 Optional.empty()
     */
    Optional<Region> findById(Long id);

    /**
     * 전체 지역 목록을 조회한다.
     *
     * @return 모든 지역 엔티티의 리스트 (데이터가 없으면 빈 리스트)
     */
    List<Region> findAll();

    /**
     * 지역을 삭제한다.
     *
     * @param region 삭제할 지역 엔티티
     */
    void delete(Region region);
}
