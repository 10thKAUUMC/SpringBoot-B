package umc.study.umc_mission.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 음식 종류(FoodKind) 엔티티.
 * "한식", "일식", "중식" 등 음식 카테고리를 저장한다.
 */
@Entity
@Table(name = "food_kind")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FoodKind extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String foodType;

    public Long getId() {
        return id;
    }

    public String getFoodType() {
        return foodType;
    }
}
