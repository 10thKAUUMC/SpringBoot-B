package umc.study.umc_mission.infrastructure.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.mission.entity.Mission;
import umc.study.umc_mission.domain.mission.repository.MissionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * MissionRepository의 JPA 구현체.
 * 내부적으로 Spring Data JPA(MissionJpaRepository)를 사용하여 DB에 접근한다.
 */
@Repository
public class MissionRepositoryImpl implements MissionRepository {

    private final MissionJpaRepository jpaRepository;

    public MissionRepositoryImpl(MissionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Mission save(Mission mission) {
        return jpaRepository.save(mission);
    }

    @Override
    public Optional<Mission> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Mission> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(Mission mission) {
        jpaRepository.delete(mission);
    }

    /**
     * 6주차 추가 — 지역 기준 도전 가능 미션을 페이징 조회한다.
     *
     * <p>"현재 시각"은 도메인 레벨에서 결정해 인프라에 넘긴다. 인프라가 LocalDateTime.now()를
     * 직접 호출하면 테스트 시 시간을 고정하기 어려우므로, 호출자가 시간 주입을 제어하도록 한다.
     * (이번 구현에서는 단순히 현재 시간을 사용.)</p>
     */
    @Override
    public Page<Mission> findChallengableByRegion(Long regionId, Pageable pageable) {
        return jpaRepository.findChallengableByRegion(regionId, LocalDateTime.now(), pageable);
    }
}
