package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.store.entity.FoodKind;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 회원-음식취향 중간 테이블.
 * Member와 FoodKind의 다대다(N:M) 관계를 풀어낸다.
 */
@Entity
@Table(name = "member_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberLikes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_kind_id", nullable = false)
    private FoodKind foodKind;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public FoodKind getFoodKind() {
        return foodKind;
    }
}
