package umc.study.umc_mission.domain.point.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.mission.entity.Mission;
import umc.study.umc_mission.domain.point.enums.PointType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 포인트 이력(PointHistory) 엔티티 — 포인트의 적립/사용 기록을 표현한다.
 *
 * <p>회원이 미션을 완료해서 포인트를 적립하거나, 포인트를 사용할 때마다
 * 한 행씩 기록이 쌓이는 "이력(History)" 테이블이다.</p>
 *
 * <p>예: "홍길동이 '15,000원 결제 미션' 완료로 500 포인트 적립" → 1행 INSERT.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>BaseEntity를 상속하지 않음 — BaseEntity는 createdAt과 updatedAt을 모두 관리하지만,
 *       포인트 이력은 한 번 기록되면 수정되지 않는 INSERT-only 데이터이므로 updatedAt이 불필요하다.
 *       따라서 BaseEntity 대신 createdAt만 직접 선언했다.</li>
 *   <li>mission 필드가 nullable — 미션과 무관한 포인트 적립/사용(예: 이벤트 보상, 포인트 차감)도
 *       있을 수 있으므로 mission은 null이 될 수 있다.</li>
 *   <li>@EntityListeners를 직접 선언 — BaseEntity를 상속하지 않으므로
 *       AuditingEntityListener를 이 클래스에 직접 등록해야 @CreatedDate가 동작한다.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 point_history 테이블과 매핑된다.
@Table(name = "point_history")  // 테이블 이름을 "point_history"로 명시.

/*
 * @EntityListeners(AuditingEntityListener.class)
 * - BaseEntity를 상속하지 않으므로, JPA Auditing 리스너를 직접 등록해야 한다.
 * - 이 설정이 있어야 @CreatedDate가 엔티티 저장 시 자동으로 현재 시간을 넣어준다.
 */
@EntityListeners(AuditingEntityListener.class)

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. 외부 직접 생성 차단.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder  // 빌더 패턴 자동 생성.
public class PointHistory {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 포인트가 적립/사용된 회원.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 한 회원이 여러 포인트 이력을 가질 수 있으므로 다대일(N:1).
     * - LAZY: 실제로 member에 접근할 때만 DB 쿼리 실행.
     *
     * @JoinColumn(name = "member_id", nullable = false)
     * - "member_id" 외래 키 컬럼 생성. 포인트 이력은 반드시 어떤 회원의 것인지 알아야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /*
     * 이 포인트 이력과 연관된 미션 (있을 경우).
     *
     * @ManyToOne(fetch = FetchType.LAZY) — 다대일 관계, 지연 로딩.
     * @JoinColumn(name = "mission_id") — nullable이 기본값(true)이므로 미션 없이도 기록 가능.
     *   예: 이벤트 보상으로 포인트를 받은 경우에는 mission이 null이다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    /*
     * 포인트 이력의 유형 (EARN: 적립, USE: 사용 등).
     *
     * @Enumerated(EnumType.STRING) — enum을 문자열로 DB에 저장.
     * @Column(nullable = false) — 유형은 필수.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointType type;

    /*
     * 적립 또는 사용된 포인트 금액.
     * 적립이면 양수, 사용이면 음수로 저장할 수도 있고, type으로 구분할 수도 있다 (설계 선택).
     * nullable = false — 금액은 반드시 있어야 한다.
     */
    @Column(nullable = false)
    private Long amount;

    /*
     * 포인트 이력에 대한 설명.
     * 예: "미션 완료 보상", "이벤트 적립" 등.
     * length = 40 — VARCHAR(40). null 허용.
     */
    @Column(length = 40)
    private String description;

    /*
     * 이력이 생성된 시간.
     *
     * @CreatedDate — 엔티티가 처음 저장될 때 자동으로 현재 시간이 기록된다.
     * @Column(updatable = false) — 한 번 기록된 생성 시간은 절대 변경되지 않도록 보호.
     *
     * BaseEntity를 상속했다면 자동으로 제공되지만,
     * 이 엔티티는 updatedAt이 필요 없어서 BaseEntity를 상속하지 않으므로 직접 선언했다.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 포인트가 적립/사용된 회원을 반환한다. */
    public Member getMember() {
        return member;
    }

    /** 연관된 미션을 반환한다. null이면 미션과 무관한 포인트 이력. */
    public Mission getMission() {
        return mission;
    }

    /** 포인트 이력의 유형(EARN, USE 등)을 반환한다. */
    public PointType getType() {
        return type;
    }

    /** 적립/사용된 포인트 금액을 반환한다. */
    public Long getAmount() {
        return amount;
    }

    /** 포인트 이력에 대한 설명을 반환한다. */
    public String getDescription() {
        return description;
    }

    /** 이력이 생성된 시간을 반환한다. */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
