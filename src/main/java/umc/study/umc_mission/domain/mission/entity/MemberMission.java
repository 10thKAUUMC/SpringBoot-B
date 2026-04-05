package umc.study.umc_mission.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.mission.enums.MissionState;
import umc.study.umc_mission.global.common.BaseEntity;

@Entity
@Table(name = "member_mission")
@Getter
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
}
