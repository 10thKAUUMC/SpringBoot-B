package umc.study.umc_mission.infrastructure.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.study.umc_mission.domain.mission.entity.MemberMission;
import umc.study.umc_mission.domain.mission.enums.MissionState;

/**
 * MemberMission용 Spring Data JPA 인터페이스.
 *
 * <p>"내 미션 목록" 화면을 위한 페이징 쿼리.
 * Mission/Store를 fetch join으로 한 번에 가져와 N+1을 방지한다.</p>
 */
public interface MemberMissionJpaRepository extends JpaRepository<MemberMission, Long> {

    /**
     * 회원-상태 조건으로 MemberMission을 페이징 조회.
     *
     * <p>fetch join으로 Mission, Store까지 즉시 로딩. 응답 DTO에서 mission.title,
     * store.name을 사용하므로 매 행마다 추가 쿼리가 발생하면 N+1 문제가 된다.</p>
     */
    @Query(
            value = "select mm from MemberMission mm " +
                    "join fetch mm.mission m " +
                    "join fetch m.store s " +
                    "where mm.member.id = :memberId " +
                    "  and mm.state = :state",
            countQuery = "select count(mm) from MemberMission mm " +
                    "where mm.member.id = :memberId " +
                    "  and mm.state = :state"
    )
    Page<MemberMission> findByMemberAndState(
            @Param("memberId") Long memberId,
            @Param("state") MissionState state,
            Pageable pageable);
}
