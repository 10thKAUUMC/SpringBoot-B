package umc.study.umc_mission.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 리뷰(Review) 엔티티 — 회원이 가게에 작성한 리뷰를 표현한다.
 *
 * <p>현실 세계의 "음식점 리뷰"를 추상화한 것이다.
 * 누가(Member), 어디에(Store), 몇 점을(rating), 무슨 내용을(content) 남겼는지를 저장한다.</p>
 *
 * <p>예: "홍길동이 '맛있는 김치찌개' 가게에 별점 5점, '정말 맛있어요!' 리뷰 작성"</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>Member와 N:1 관계 — 한 회원이 여러 리뷰를 작성할 수 있다.</li>
 *   <li>Store와 N:1 관계 — 한 가게에 여러 리뷰가 달릴 수 있다.</li>
 *   <li>rating을 String(length=1)으로 저장 — "1"~"5" 한 자리 문자.
 *       Integer 대신 String을 쓴 것은 설계 선택이며, 비즈니스 요구에 따라 변경 가능.</li>
 *   <li>content에 TEXT 타입 — 리뷰 내용이 길어질 수 있으므로.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 review 테이블과 매핑된다.
@Table(name = "review")  // 테이블 이름을 "review"로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. 외부 직접 생성 차단.
@AllArgsConstructor  // Builder 내부에서 사용하는 전체 필드 생성자.
@Builder  // 빌더 패턴 자동 생성.
public class Review extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 리뷰를 작성한 회원.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 한 회원이 여러 리뷰를 작성할 수 있으므로 다대일(N:1) 관계.
     * - LAZY: 리뷰를 조회할 때 회원 정보를 즉시 가져오지 않는다.
     *   member 필드에 접근하는 순간에야 쿼리가 실행된다 (지연 로딩).
     *
     * @JoinColumn(name = "member_id", nullable = false)
     * - review 테이블에 "member_id" 외래 키 컬럼 생성.
     * - nullable = false: 리뷰는 반드시 작성자(회원)가 있어야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /*
     * 리뷰가 달린 가게.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 한 가게에 여러 리뷰가 달릴 수 있으므로 다대일(N:1) 관계.
     * - LAZY: 지연 로딩으로 성능 최적화.
     *
     * @JoinColumn(name = "store_id", nullable = false)
     * - "store_id" 외래 키 컬럼 생성. 리뷰는 반드시 어떤 가게에 대한 것인지 알아야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    /*
     * 별점 (1~5). 한 자리 문자열로 저장.
     * nullable = false — 별점은 필수.
     * length = 1 — VARCHAR(1). "1", "2", "3", "4", "5" 중 하나.
     *
     * 참고: Integer 대신 String을 사용한 것은 설계 선택이다.
     * 실무에서는 Integer로 저장하고 검증 로직을 추가하는 경우가 더 일반적이다.
     */
    @Column(nullable = false, length = 1)
    private String rating;

    /*
     * 리뷰 내용. 예: "김치찌개가 정말 맛있었어요! 또 오고 싶습니다."
     *
     * @Column(columnDefinition = "TEXT")
     * - 리뷰가 길어질 수 있으므로 VARCHAR 대신 TEXT 타입 사용.
     * - nullable (기본값 true) — 별점만 남기고 내용은 안 쓸 수도 있다.
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 리뷰를 작성한 회원을 반환한다. */
    public Member getMember() {
        return member;
    }

    /** 리뷰가 달린 가게를 반환한다. */
    public Store getStore() {
        return store;
    }

    /** 별점(1~5)을 반환한다. */
    public String getRating() {
        return rating;
    }

    /** 리뷰 내용을 반환한다. null이면 내용 없이 별점만 남긴 리뷰. */
    public String getContent() {
        return content;
    }
}
