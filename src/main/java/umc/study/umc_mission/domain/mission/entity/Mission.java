package umc.study.umc_mission.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.mission.enums.MissionType;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.global.common.BaseEntity;

import java.time.LocalDateTime;

/**
 * 미션(Mission) 엔티티 — 가게에서 진행하는 미션 정보를 표현한다.
 *
 * <p>현실 세계의 "가게 미션"을 추상화한 것이다.
 * 예: "이 가게에서 15,000원 이상 결제하면 500 포인트 적립!"</p>
 *
 * <p>미션은 반드시 하나의 가게(Store)에 소속되며,
 * 미션 유형(type), 보상(reward), 만료일(expiredAt) 등의 정보를 가진다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>Store와 N:1 관계 — 하나의 가게가 여러 미션을 가질 수 있다.</li>
 *   <li>content 필드에 @Column(columnDefinition = "TEXT") — 미션 설명이 길어질 수 있으므로
 *       VARCHAR 대신 TEXT 타입을 사용한다.</li>
 *   <li>expiredAt이 null이면 "만료 기한 없음"을 의미한다.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 mission 테이블과 매핑된다.
@Table(name = "mission")  // 테이블 이름을 "mission"으로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. PROTECTED로 외부 직접 생성 차단.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder  // 빌더 패턴 자동 생성. Mission.builder().title("미션1").reward(500L).build() 형태로 사용.
public class Mission extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 이 미션이 속한 가게.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 하나의 가게(Store)가 여러 미션을 가질 수 있으므로 다대일(N:1) 관계.
     * - LAZY: 미션을 조회할 때 가게 정보를 즉시 가져오지 않는다.
     *   store 필드에 접근하는 순간에야 SELECT 쿼리가 실행된다 (지연 로딩, 성능 최적화).
     *
     * @JoinColumn(name = "store_id", nullable = false)
     * - mission 테이블에 "store_id" 외래 키(FK) 컬럼이 생긴다.
     * - nullable = false: 미션은 반드시 어떤 가게에 소속되어야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    /*
     * 미션의 유형 (예: PURCHASE — 결제 미션, VISIT — 방문 미션 등).
     *
     * @Enumerated(EnumType.STRING) — enum을 문자열로 DB에 저장.
     *   숫자(ORDINAL)로 저장하면 enum 순서 변경 시 데이터가 꼬이므로 반드시 STRING 사용.
     * @Column(nullable = false, length = 15) — 미션 유형은 필수이며, VARCHAR(15)으로 저장.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private MissionType type;

    /*
     * 미션의 제목. 예: "15,000원 이상 결제하기".
     * nullable = false — 제목은 반드시 있어야 한다.
     * length = 30 — VARCHAR(30).
     */
    @Column(nullable = false, length = 30)
    private String title;

    /*
     * 미션의 상세 설명.
     *
     * @Column(columnDefinition = "TEXT")
     * - 설명이 길어질 수 있으므로 VARCHAR 대신 TEXT 타입을 사용한다.
     * - VARCHAR는 최대 길이가 제한되지만, TEXT는 65,535바이트까지 저장 가능.
     * - nullable (기본값 true) — 설명이 없을 수도 있다.
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /*
     * 미션 완료 시 받는 보상 포인트.
     * nullable = false — 보상은 반드시 있어야 한다 (0포인트라도 명시해야 함).
     */
    @Column(nullable = false)
    private Long reward;

    /*
     * 미션의 만료 일시.
     * null이면 "만료 기한 없음"을 의미한다.
     * LocalDateTime은 날짜 + 시간을 모두 포함한다 (예: 2024-12-31T23:59:59).
     */
    private LocalDateTime expiredAt;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 이 미션이 속한 가게를 반환한다. */
    public Store getStore() {
        return store;
    }

    /** 미션의 유형(PURCHASE, VISIT 등)을 반환한다. */
    public MissionType getType() {
        return type;
    }

    /** 미션의 제목을 반환한다. */
    public String getTitle() {
        return title;
    }

    /** 미션의 상세 설명을 반환한다. */
    public String getContent() {
        return content;
    }

    /** 미션 완료 시 받는 보상 포인트를 반환한다. */
    public Long getReward() {
        return reward;
    }

    /** 미션의 만료 일시를 반환한다. null이면 만료 기한 없음. */
    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
