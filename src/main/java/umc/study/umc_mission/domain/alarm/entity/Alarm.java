package umc.study.umc_mission.domain.alarm.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.alarm.enums.AlarmType;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 알림(Alarm) 엔티티.
 * 회원에게 전송되는 알림 정보를 저장한다.
 */
@Entity
@Table(name = "alarm")
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

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public AlarmType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
