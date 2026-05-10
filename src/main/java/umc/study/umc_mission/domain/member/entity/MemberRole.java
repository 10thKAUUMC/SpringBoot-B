package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.enums.RoleType;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 회원-역할 매핑(MemberRole) 엔티티 — 회원이 어떤 역할을 갖고 있는지를 표현한다.
 *
 * <p>한 회원이 여러 역할(USER, ADMIN 등)을 가질 수 있다.
 * 예를 들어 일반 사용자이면서 동시에 관리자일 수 있다.</p>
 *
 * <p>Role을 별도 테이블 대신 Enum(RoleType)으로 관리하여 단순화했다.
 * 역할의 종류가 적고 자주 변하지 않으므로 Enum이 적합하다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>Member와 N:1 관계 — 여러 MemberRole이 하나의 Member에 속한다.</li>
 *   <li>@Builder.Default로 roleType 기본값을 USER로 설정 — 대부분의 회원은 일반 사용자이므로.</li>
 *   <li>FetchType.LAZY — Member를 조회할 때 MemberRole을 즉시 가져오지 않고,
 *       실제로 접근할 때 쿼리를 날린다 (성능 최적화).</li>
 * </ul>
 */

@Entity  // JPA 엔티티임을 선언. DB의 member_role 테이블과 매핑된다.
@Table(name = "member_role")  // 매핑될 테이블 이름을 "member_role"로 명시.

/*
 * @NoArgsConstructor(access = AccessLevel.PROTECTED)
 * - JPA가 내부적으로 사용할 기본 생성자를 PROTECTED로 생성.
 * - 외부에서 new MemberRole()을 호출하지 못하게 막아, Builder를 통한 생성을 유도한다.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder  // 빌더 패턴 자동 생성.
public class MemberRole extends BaseEntity {

    /*
     * 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번한다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 이 역할이 속한 회원.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 여러 MemberRole이 하나의 Member에 속하므로 다대일(N:1) 관계.
     * - FetchType.LAZY: MemberRole을 조회할 때 Member를 즉시 JOIN으로 가져오지 않는다.
     *   member 필드에 실제로 접근(getMember())하는 순간에야 SELECT 쿼리가 실행된다.
     *   이를 "지연 로딩"이라 하며, 불필요한 쿼리를 줄여 성능을 높인다.
     *
     * @JoinColumn(name = "member_id", nullable = false)
     * - member_role 테이블에 "member_id"라는 외래 키(FK) 컬럼이 생긴다.
     * - nullable = false: 역할은 반드시 어떤 회원에 소속되어야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /*
     * 역할의 종류 (USER, ADMIN 등).
     *
     * @Enumerated(EnumType.STRING) — enum 값을 "USER", "ADMIN" 같은 문자열로 DB에 저장.
     * @Column(nullable = false, length = 10) — 역할은 필수이며, VARCHAR(10)으로 저장.
     * @Builder.Default — Builder 사용 시 roleType을 지정하지 않으면 기본값 USER가 들어간다.
     *   대부분의 회원은 일반 사용자이므로, 생성 시 매번 USER를 명시하지 않아도 되도록 편의를 제공.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private RoleType roleType = RoleType.USER;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 이 역할이 속한 회원을 반환한다. */
    public Member getMember() {
        return member;
    }

    /** 역할의 종류(USER, ADMIN 등)를 반환한다. */
    public RoleType getRoleType() {
        return roleType;
    }
}
