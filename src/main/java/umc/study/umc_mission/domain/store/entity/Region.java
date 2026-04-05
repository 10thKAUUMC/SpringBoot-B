package umc.study.umc_mission.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 지역(Region) 엔티티.
 * "서울", "부산" 등 지역 정보를 저장한다.
 */
@Entity
@Table(name = "region")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
