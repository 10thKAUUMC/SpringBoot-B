package umc.study.umc_mission.infrastructure.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.mission.entity.MemberMission;
import umc.study.umc_mission.domain.mission.enums.MissionState;
import umc.study.umc_mission.domain.mission.repository.MemberMissionRepository;

/**
 * MemberMissionRepository의 JPA 구현체.
 */
@Repository
public class MemberMissionRepositoryImpl implements MemberMissionRepository {

    private final MemberMissionJpaRepository jpaRepository;

    public MemberMissionRepositoryImpl(MemberMissionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Page<MemberMission> findByMemberAndState(Long memberId, MissionState state, Pageable pageable) {
        return jpaRepository.findByMemberAndState(memberId, state, pageable);
    }
}
