package umc.study.umc_mission.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.global.common.BaseEntity;

@Entity
@Table(name = "region")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;
}
