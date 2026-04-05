package umc.study.umc_mission.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.mission.enums.MissionState;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 회원-미션 매핑(MemberMission) 엔티티 — 회원이 미션에 참여한 기록과 진행 상태를 표현한다.
 *
 * <p>Member와 Mission의 다대다(N:M) 관계를 풀어내는 중간 테이블이다.
 * 한 회원이 여러 미션에 참여할 수 있고, 하나의 미션에 여러 회원이 참여할 수 있다.</p>
 *
 * <p>예를 들어 "홍길동"이 "15,000원 이상 결제하기" 미션에 도전 중이라면,
 * member_mission 테이블에 { member_id=1, mission_id=3, state=CHALLENGING } 행이 생긴다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>state 필드로 미션의 진행 상태(CHALLENGING, COMPLETE 등)를 추적한다.</li>
 *   <li>@Builder.Default로 기본 상태를 CHALLENGING으로 설정 — 미션에 참여하면 처음에는 "도전 중"이니까.</li>
 *   <li>MemberLikes와 동일하게, @ManyToMany 대신 중간 엔티티를 직접 만들어
 *       state 같은 추가 정보를 자유롭게 담을 수 있다.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 member_mission 테이블과 매핑된다.
@Table(name = "member_mission")  // 테이블 이름을 "member_mission"으로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. 외부 직접 생성 차단.
@AllArgsConstructor  // Builder 내부에서 사용하는 전체 필드 생성자.
@Builder  // 빌더 패턴 자동 생성.
public class MemberMission extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 미션에 참여한 회원.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 여러 MemberMission이 하나의 Member에 속하므로 다대일(N:1) 관계.
     * - LAZY: 실제로 member에 접근할 때만 DB 쿼리 실행 (지연 로딩).
     *
     * @JoinColumn(name = "member_id", nullable = false)
     * - "member_id" 외래 키 컬럼 생성. 반드시 어떤 회원의 참여 기록인지 알아야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /*
     * 참여 대상 미션.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 여러 MemberMission이 하나의 Mission을 가리킬 수 있으므로 다대일(N:1).
     * - 예: 같은 미션에 여러 회원이 도전할 수 있다.
     *
     * @JoinColumn(name = "mission_id", nullable = false)
     * - "mission_id" 외래 키 컬럼 생성. 반드시 어떤 미션인지 지정해야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    /*
     * 미션 진행 상태 (CHALLENGING: 도전 중, COMPLETE: 완료 등).
     *
     * @Enumerated(EnumType.STRING) — enum을 문자열("CHALLENGING", "COMPLETE")로 DB에 저장.
     * @Column(nullable = false, length = 10) — 상태는 필수이며, VARCHAR(10)으로 저장.
     * @Builder.Default — Builder로 생성할 때 state를 지정하지 않으면 기본값 CHALLENGING이 들어간다.
     *   미션에 참여하는 순간은 항상 "도전 중" 상태이므로, 매번 명시하지 않아도 되도록 편의 제공.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private MissionState state = MissionState.CHALLENGING;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 미션에 참여한 회원을 반환한다. */
    public Member getMember() {
        return member;
    }

    /** 참여 대상 미션을 반환한다. */
    public Mission getMission() {
        return mission;
    }

    /** 미션의 현재 진행 상태(CHALLENGING, COMPLETE 등)를 반환한다. */
    public MissionState getState() {
        return state;
    }
}
