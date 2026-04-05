package umc.study.umc_mission.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.mission.enums.MissionState;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 회원-미션 중간 테이블.
 * 회원이 미션에 참여한 기록과 진행 상태(state)를 저장한다.
 */
@Entity
@Table(name = "member_mission")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private MissionState state = MissionState.CHALLENGING;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Mission getMission() {
        return mission;
    }

    public MissionState getState() {
        return state;
    }
}
