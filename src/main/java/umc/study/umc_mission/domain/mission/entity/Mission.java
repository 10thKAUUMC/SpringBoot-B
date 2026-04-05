package umc.study.umc_mission.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.mission.enums.MissionType;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.global.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "mission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private MissionType type;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long reward;

    private LocalDateTime expiredAt;
}
