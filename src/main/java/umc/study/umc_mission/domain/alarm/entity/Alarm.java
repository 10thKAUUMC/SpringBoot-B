package umc.study.umc_mission.domain.alarm.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.alarm.enums.AlarmType;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 알림(Alarm) 엔티티 — 회원에게 전송되는 알림 정보를 표현한다.
 *
 * <p>서비스에서 회원에게 보내는 다양한 알림을 저장한다.
 * 예: "미션 완료!", "새 리뷰가 달렸습니다", "포인트가 적립되었습니다" 등.</p>
 *
 * <p>알림의 유형(type)에 따라 다른 종류의 알림을 구분하고,
 * isConfirmed로 회원이 이 알림을 확인했는지 추적한다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>Member와 N:1 관계 — 한 회원이 여러 알림을 받을 수 있다.</li>
 *   <li>isConfirmed 기본값 false — 알림은 생성 시점에는 아직 확인되지 않은 상태.</li>
 *   <li>content에 TEXT 타입 — 알림 내용이 길어질 수 있으므로.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 alarm 테이블과 매핑된다.
@Table(name = "alarm")  // 테이블 이름을 "alarm"으로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. 외부 직접 생성 차단.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder  // 빌더 패턴 자동 생성.
public class Alarm extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 이 알림을 받는 회원.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 한 회원이 여러 알림을 받을 수 있으므로 다대일(N:1) 관계.
     * - LAZY: 알림을 조회할 때 회원 정보를 즉시 가져오지 않는다.
     *   member 필드에 접근하는 순간에야 쿼리가 실행된다 (지연 로딩).
     *
     * @JoinColumn(name = "member_id", nullable = false)
     * - alarm 테이블에 "member_id" 외래 키 컬럼 생성.
     * - nullable = false: 알림은 반드시 수신자(회원)가 있어야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /*
     * 회원이 이 알림을 확인했는지 여부.
     *
     * @Builder.Default — Builder로 생성할 때 isConfirmed를 지정하지 않으면 기본값 false가 들어간다.
     *   알림은 생성 시점에 아직 확인되지 않은 상태이므로 false가 합리적이다.
     *   회원이 알림을 클릭/읽으면 이 값이 true로 변경된다.
     * @Column(nullable = false) — 확인 여부는 반드시 값이 있어야 한다 (null 불가).
     */
    @Builder.Default
    @Column(nullable = false)
    private Boolean isConfirmed = false;

    /*
     * 알림의 유형 (예: MISSION_COMPLETE, REVIEW_CREATED, POINT_EARNED 등).
     *
     * @Enumerated(EnumType.STRING) — enum을 문자열로 DB에 저장.
     *   숫자(ORDINAL)로 저장하면 enum 순서 변경 시 데이터가 꼬이므로 반드시 STRING 사용.
     * @Column(nullable = false, length = 15) — 알림 유형은 필수이며, VARCHAR(15)으로 저장.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AlarmType type;

    /*
     * 알림의 제목. 예: "미션 완료!", "포인트 적립 알림".
     * nullable = false — 제목은 필수.
     * length = 30 — VARCHAR(30).
     */
    @Column(nullable = false, length = 30)
    private String title;

    /*
     * 알림의 상세 내용.
     *
     * @Column(columnDefinition = "TEXT")
     * - 알림 내용이 길어질 수 있으므로 VARCHAR 대신 TEXT 타입 사용.
     * - nullable (기본값 true) — 제목만 있고 내용이 없는 알림도 가능.
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 이 알림을 받는 회원을 반환한다. */
    public Member getMember() {
        return member;
    }

    /** 회원이 이 알림을 확인했는지 여부를 반환한다. true면 확인됨, false면 미확인. */
    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    /** 알림의 유형(MISSION_COMPLETE, REVIEW_CREATED 등)을 반환한다. */
    public AlarmType getType() {
        return type;
    }

    /** 알림의 제목을 반환한다. */
    public String getTitle() {
        return title;
    }

    /** 알림의 상세 내용을 반환한다. null이면 내용 없는 알림. */
    public String getContent() {
        return content;
    }
}
