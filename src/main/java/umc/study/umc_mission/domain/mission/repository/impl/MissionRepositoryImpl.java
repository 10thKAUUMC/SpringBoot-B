package umc.study.umc_mission.domain.mission.repository.impl;

import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.mission.entity.Mission;
import umc.study.umc_mission.domain.mission.repository.MissionJpaRepository;
import umc.study.umc_mission.domain.mission.repository.MissionRepository;

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
}
