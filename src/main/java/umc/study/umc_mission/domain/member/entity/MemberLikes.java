package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.store.entity.FoodKind;
import umc.study.umc_mission.global.common.BaseEntity;

@Entity
@Table(name = "member_likes")
@Getter
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
}
