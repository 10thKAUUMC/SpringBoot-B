package umc.study.umc_mission.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 리뷰(Review) 엔티티.
 * 회원이 가게에 작성한 리뷰를 저장한다.
 */
@Entity
@Table(name = "review")
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

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Store getStore() {
        return store;
    }

    public String getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }
}
