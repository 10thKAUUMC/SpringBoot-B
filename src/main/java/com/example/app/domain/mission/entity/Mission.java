package com.example.app.domain.mission.entity;

import com.example.app.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer rewardPoint;

    @Column(length = 100)
    private String missionType;

    @OneToMany(mappedBy = "mission")
    @Builder.Default
    private List<StoreMission> storeMissions = new ArrayList<>();

    @OneToMany(mappedBy = "mission")
    @Builder.Default
    private List<UserMission> userMissions = new ArrayList<>();
}
