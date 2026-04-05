package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.store.entity.FoodCategory;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 회원-음식취향 매핑(MemberLikes) 엔티티 — 회원이 어떤 음식 카테고리를 좋아하는지를 표현한다.
 *
 * <p>Member와 FoodCategory의 다대다(N:M) 관계를 풀어내는 중간 테이블이다.
 * 예를 들어 "홍길동" 회원이 "한식"과 "일식"을 좋아하면, member_likes 테이블에 두 행이 생긴다.</p>
 *
 * <p>왜 @ManyToMany를 직접 쓰지 않고 중간 엔티티를 만들었는가?</p>
 * <ul>
 *   <li>@ManyToMany는 JPA가 자동으로 중간 테이블을 만들어주지만, 나중에 중간 테이블에
 *       추가 컬럼(예: 선호도 점수, 등록일 등)을 넣기 어렵다.</li>
 *   <li>중간 엔티티를 직접 만들면 확장성이 좋고, 관계를 더 명확하게 제어할 수 있다.</li>
 *   <li>실무에서는 거의 항상 중간 엔티티를 직접 만드는 방식을 선호한다.</li>
 * </ul>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>Member 쪽에서 cascade = ALL, orphanRemoval = true로 관리되므로,
 *       회원 삭제 시 좋아요 데이터도 함께 삭제된다.</li>
 *   <li>양쪽 FK 모두 nullable = false — 회원과 음식 카테고리 모두 반드시 존재해야 한다.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 member_likes 테이블과 매핑된다.
@Table(name = "member_likes")  // 테이블 이름을 "member_likes"로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자 (외부 호출 차단).
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder  // 빌더 패턴 자동 생성.
public class MemberLikes extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 이 좋아요 기록이 속한 회원.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 여러 MemberLikes가 하나의 Member에 속하므로 다대일(N:1) 관계.
     * - LAZY: 실제로 member에 접근할 때만 DB 쿼리를 실행 (지연 로딩).
     *
     * @JoinColumn(name = "member_id", nullable = false)
     * - member_likes 테이블에 "member_id" 외래 키 컬럼 생성.
     * - nullable = false: 어떤 회원의 좋아요인지 반드시 알아야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /*
     * 회원이 좋아하는 음식 카테고리.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 여러 MemberLikes가 하나의 FoodCategory를 가리킬 수 있으므로 다대일(N:1).
     * - 예: 여러 회원이 "한식"을 좋아할 수 있다.
     *
     * @JoinColumn(name = "food_category_id", nullable = false)
     * - "food_category_id" 외래 키 컬럼 생성. 반드시 어떤 카테고리인지 지정해야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 이 좋아요 기록이 속한 회원을 반환한다. */
    public Member getMember() {
        return member;
    }

    /** 회원이 좋아하는 음식 카테고리를 반환한다. */
    public FoodCategory getFoodCategory() {
        return foodCategory;
    }
}
