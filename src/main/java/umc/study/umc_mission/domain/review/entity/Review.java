package umc.study.umc_mission.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.global.common.BaseEntity;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false, length = 1)
    private String rating;

    @Column(columnDefinition = "TEXT")
    private String content;
}
