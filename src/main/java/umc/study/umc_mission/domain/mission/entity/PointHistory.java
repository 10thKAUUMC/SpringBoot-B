package umc.study.umc_mission.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.mission.enums.PointType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 포인트 이력(PointHistory) 엔티티.
 * 포인트 적립/사용 기록을 저장한다.
 * BaseEntity를 상속하지 않음 — updatedAt이 필요 없는 INSERT-only 데이터.
 */
@Entity
@Table(name = "point_history")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointType type;

    @Column(nullable = false)
    private Long amount;

    @Column(length = 40)
    private String description;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Mission getMission() {
        return mission;
    }

    public PointType getType() {
        return type;
    }

    public Long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
