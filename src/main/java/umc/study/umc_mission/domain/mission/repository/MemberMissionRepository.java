package umc.study.umc_mission.domain.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.study.umc_mission.domain.mission.entity.MemberMission;
import umc.study.umc_mission.domain.mission.enums.MissionState;

/**
 * MemberMission(회원-미션 참여 기록) 도메인의 Repository 인터페이스.
 *
 * <p>6주차 신설. 헥사고날 컨벤션에 따라 도메인이 정의한 포트만 둔다.
 * 구현체는 {@code infrastructure/mission/repository/MemberMissionRepositoryImpl}.</p>
 */
public interface MemberMissionRepository {

    /**
     * 특정 회원의 특정 상태(CHALLENGING, COMPLETE) 미션을 페이징 조회한다.
     *
     * @param memberId 회원 PK
     * @param state    필터링 대상 상태
     * @param pageable Spring Data Pageable
     * @return 회원의 해당 상태 미션 페이지 (Mission/Store가 함께 fetch됨)
     */
    Page<MemberMission> findByMemberAndState(Long memberId, MissionState state, Pageable pageable);
}
