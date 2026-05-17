package com.example.app.domain.mission.entity;

import com.example.app.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_mission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class StoreMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeMissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;
}
