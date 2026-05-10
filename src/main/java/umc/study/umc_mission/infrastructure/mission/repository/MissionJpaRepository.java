package umc.study.umc_mission.infrastructure.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.study.umc_mission.domain.mission.entity.Mission;

import java.time.LocalDateTime;

/**
 * Spring Data JPA가 자동으로 구현체를 생성해주는 인터페이스.
 * MissionRepositoryImpl 내부에서만 사용된다.
 *
 * <p>6주차 추가: 지역 기준 도전 가능 미션을 페이징 조회하는 JPQL 쿼리.
 * Mission → Store → Region 경로를 JOIN으로 풀고, 만료되지 않은 미션만 필터링한다.</p>
 */
public interface MissionJpaRepository extends JpaRepository<Mission, Long> {

    /**
     * 특정 지역에 속한 가게들의 "도전 가능한" 미션을 페이징해서 조회한다.
     *
     * <p>도전 가능 조건: 만료일이 NULL이거나, 현재 시각보다 미래.</p>
     *
     * <p>{@code countQuery}를 명시한 이유: Page는 totalCount 계산을 위해 추가 COUNT 쿼리를
     * 발행하는데, 기본 카운트 쿼리는 메인 쿼리 형태에 따라 비효율이 될 수 있다.
     * 명시적으로 단순한 카운트 쿼리를 작성해 성능을 챙긴다.</p>
     */
    @Query(
            value = "select m from Mission m " +
                    "join m.store s " +
                    "join s.region r " +
                    "where r.id = :regionId " +
                    "  and (m.expiredAt is null or m.expiredAt > :now)",
            countQuery = "select count(m) from Mission m " +
                    "join m.store s " +
                    "join s.region r " +
                    "where r.id = :regionId " +
                    "  and (m.expiredAt is null or m.expiredAt > :now)"
    )
    Page<Mission> findChallengableByRegion(
            @Param("regionId") Long regionId,
            @Param("now") LocalDateTime now,
            Pageable pageable);
}
