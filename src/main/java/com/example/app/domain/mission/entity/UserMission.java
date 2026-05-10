package com.example.app.domain.mission.entity;

import com.example.app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_mission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(length = 50)
    private String status; // IN_PROGRESS, COMPLETED, FAILED

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    public void complete() {
        this.status = "COMPLETED";
        this.completedAt = LocalDateTime.now();
    }
}
