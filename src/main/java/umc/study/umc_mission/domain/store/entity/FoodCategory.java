package umc.study.umc_mission.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 음식 카테고리(FoodCategory) 엔티티.
 * 가게가 취급하는 음식 분류("한식", "일식", "중식" 등)를 저장한다.
 * 회원의 음식 취향(MemberLikes)과 연결된다.
 */
@Entity
@Table(name = "food_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
