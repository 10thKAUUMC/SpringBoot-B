package umc.study.umc_mission.domain.alarm.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.alarm.enums.AlarmType;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.global.common.BaseEntity;

@Entity
@Table(name = "alarm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isConfirmed = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AlarmType type;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
}
