package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import com.example.umc10th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_missions")

public class UserMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private UserMissionStatus status;

    @Column(name = "started_at" , nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at" )
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id" , nullable = false)
    private Mission mission;


}
