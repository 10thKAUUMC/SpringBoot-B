package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.enums.Gender;
import umc.study.umc_mission.domain.mission.entity.MemberMission;
import umc.study.umc_mission.domain.review.entity.Review;
import umc.study.umc_mission.global.common.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 회원(Member) 엔티티 — 서비스를 이용하는 사용자를 표현한다.
 *
 * <p>현실의 "회원"에서 서비스에 필요한 속성만 추출(추상화)한 것이다.
 * DB의 member 테이블과 1:1로 매핑된다.</p>
 *
 * <p>회원은 이름, 닉네임, 이메일 같은 기본 정보뿐 아니라,
 * 보유 포인트, 클리어한 미션 수 같은 서비스 활동 데이터도 갖고 있다.
 * 또한 역할(MemberRole)과 음식 취향(MemberLikes)을 1:N 관계로 소유한다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>@NoArgsConstructor(PROTECTED) — JPA는 엔티티를 DB에서 읽어올 때 기본 생성자가 필요하다.
 *       하지만 PROTECTED로 접근을 제한하여, 외부에서 new Member()로 무분별하게 객체를 만드는 것을 방지한다.</li>
 *   <li>@Builder — 필드가 많은 엔티티를 생성할 때 가독성 높은 빌더 패턴을 자동으로 제공한다.
 *       예: Member.builder().name("홍길동").email("test@test.com").build()</li>
 *   <li>@Builder.Default — Builder 패턴으로 객체를 만들 때, point와 missionClear에
 *       기본값(0)이 보장되도록 한다. 이 어노테이션이 없으면 Builder가 기본값을 무시하고 null을 넣는다.</li>
 *   <li>Collections.unmodifiableList — 컬렉션 필드의 Getter에서 수정 불가능한 읽기 전용 리스트를 반환하여
 *       외부 코드가 .add()나 .remove()로 내부 상태를 함부로 바꾸지 못하게 한다.</li>
 * </ul>
 */

@Entity  // 이 클래스가 DB 테이블과 매핑되는 JPA 엔티티임을 선언한다. 이게 없으면 JPA가 이 클래스를 무시한다.
@Table(name = "member")  // 매핑될 테이블 이름을 명시적으로 "member"로 지정. 생략하면 클래스 이름이 테이블명이 된다.

/*
 * @NoArgsConstructor(access = AccessLevel.PROTECTED)
 * - 파라미터가 없는 기본 생성자를 만들어준다 (Lombok이 자동 생성).
 * - JPA 스펙상 엔티티는 반드시 기본 생성자가 있어야 한다 (리플렉션으로 객체를 생성하기 때문).
 * - PROTECTED로 설정하면, 같은 패키지 또는 자식 클래스에서만 호출 가능하고,
 *   외부(서비스 계층 등)에서 new Member()를 직접 호출하는 것은 막을 수 있다.
 * - 즉, "JPA는 쓸 수 있지만, 개발자가 실수로 빈 객체를 만드는 건 방지"하는 전략이다.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)

/*
 * @Builder
 * - 빌더 패턴(Builder Pattern)을 자동 생성해준다.
 * - 필드가 많을 때 생성자 대신 .name("홍길동").email("test@test.com") 형태로
 *   어떤 값이 어떤 필드에 들어가는지 명확하게 객체를 만들 수 있다.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BaseEntity {

    /*
     * @Id — 이 필드가 테이블의 기본 키(Primary Key)임을 선언한다.
     *        모든 JPA 엔티티는 반드시 @Id가 있어야 한다.
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * - 기본 키 값을 DB가 자동으로 생성하도록 위임한다.
     * - IDENTITY 전략: MySQL의 AUTO_INCREMENT를 사용하여 INSERT 시 DB가 알아서 1, 2, 3... 을 부여한다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 회원의 실명.
     * @Column(nullable = false) — DB에서 NOT NULL 제약 조건을 건다. 이름은 반드시 있어야 하므로.
     * length = 10 — VARCHAR(10)으로 생성된다. 한글 이름은 대부분 2~4자이므로 10자면 충분하다.
     */
    @Column(nullable = false, length = 10)
    private String name;

    /*
     * 회원의 닉네임 (서비스에서 표시되는 별명).
     * nullable이 기본값(true)이므로 닉네임 없이 가입 가능.
     * length = 20 — VARCHAR(20).
     */
    @Column(length = 20)
    private String nickname;

    /*
     * 회원의 이메일 주소. 로그인이나 알림에 사용된다.
     * nullable = false — 이메일은 필수 입력.
     * length = 30 — VARCHAR(30).
     */
    @Column(nullable = false, length = 30)
    private String email;

    /*
     * 8주차 추가 — 폼 로그인용 비밀번호 (BCrypt 해시 저장).
     *
     * length = 60: BCrypt 해시 결과 길이가 60자(고정).
     * nullable = true(기본): 4~7주차 데이터(비밀번호 없이 생성된 회원)와의 호환성을 위해 NOT NULL 강제하지 않음.
     *   추후 모든 회원이 폼 로그인 가능해지면 NOT NULL로 마이그레이션.
     */
    @Column(length = 60)
    private String password;

    /*
     * 회원의 성별.
     *
     * @Enumerated(EnumType.STRING)
     * - Gender enum을 DB에 저장할 때 "문자열"(MALE, FEMALE)로 저장한다.
     * - 만약 EnumType.ORDINAL(기본값)을 쓰면 0, 1 같은 숫자로 저장되는데,
     *   나중에 enum 순서가 바뀌면 데이터가 꼬이므로 반드시 STRING을 사용해야 한다.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    /* 회원의 생년월일. LocalDate는 날짜만 저장 (시간 없음). */
    private LocalDate birth;

    /* 회원의 전화번호. 예: "010-1234-5678" → 최대 15자. */
    @Column(length = 15)
    private String phoneNum;

    /* 회원의 주소. 예: "서울시 강남구 역삼동". */
    @Column(length = 50)
    private String address;

    /*
     * 회원이 보유한 포인트.
     *
     * @Builder.Default — Builder로 Member를 생성할 때 point를 명시하지 않으면 기본값 0L이 들어간다.
     *   이 어노테이션이 없으면, Builder가 기본값을 무시하고 null을 넣어버린다.
     *   (Java 필드 초기화(= 0L)는 new Member()에만 적용되고, Builder에는 적용되지 않기 때문.)
     *
     * @Column(nullable = false) — 포인트는 반드시 값이 있어야 한다 (null 불가).
     */
    @Builder.Default
    @Column(nullable = false)
    private Long point = 0L;

    /*
     * 회원이 완료(클리어)한 미션의 누적 수.
     * 미션을 완료할 때마다 이 값이 +1 된다.
     * @Builder.Default로 기본값 0 보장.
     */
    @Builder.Default
    @Column(nullable = false)
    private Integer missionClear = 0;

    /*
     * 이 회원이 가진 역할(Role) 목록. 예: USER, ADMIN.
     * 하나의 회원이 여러 역할을 가질 수 있으므로 1:N(OneToMany) 관계이다.
     *
     * @OneToMany(mappedBy = "member")
     * - "MemberRole 엔티티의 member 필드가 외래 키(FK)를 관리한다"는 뜻.
     * - 즉, member_role 테이블에 member_id FK 컬럼이 존재하고, 그쪽이 관계의 주인(owning side)이다.
     *
     * cascade = CascadeType.ALL
     * - Member를 저장/삭제할 때, 연결된 MemberRole도 함께 저장/삭제된다.
     * - 예: 회원을 삭제하면 그 회원의 역할 정보도 자동으로 삭제.
     *
     * orphanRemoval = true
     * - 부모(Member)의 리스트에서 자식(MemberRole)을 제거하면, DB에서도 해당 행이 삭제된다.
     * - 예: memberRoles.remove(someRole) → DELETE SQL 자동 실행.
     *
     * @Builder.Default — Builder 사용 시에도 빈 ArrayList가 보장된다 (null 방지).
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberRole> memberRoles = new ArrayList<>();

    /*
     * 이 회원이 좋아하는 음식 카테고리 목록.
     * Member와 FoodCategory의 다대다(N:M) 관계를 MemberLikes 중간 테이블로 풀어낸 것.
     * cascade, orphanRemoval 설정은 memberRoles와 동일한 이유.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberLikes> memberLikes = new ArrayList<>();

    /*
     * 6주차 추가 — 회원이 작성한 리뷰 목록 (1:N).
     * mappedBy = "member": Review 엔티티의 member 필드가 FK 주인.
     * 양방향 매핑을 두는 이유: 도메인 객체 그래프 탐색(member.getReviews())이 자연스럽고,
     * 테스트/대량 삭제(cascade)에서 부모 기준으로 다룰 수 있기 때문.
     * cascade는 회원 삭제 시 리뷰까지 일괄 정리되도록 ALL + orphanRemoval.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    /*
     * 6주차 추가 — 회원의 미션 참여 기록(진행중/완료) 목록 (1:N).
     * 마이페이지·내 미션 화면에서 member 기준으로 조회/페이징하는 데 사용된다.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberMission> memberMissions = new ArrayList<>();

    // === Getter 메서드 (필요한 필드만 외부에 노출) ===
    // Lombok의 @Getter를 사용하지 않고 직접 작성한 이유:
    // 컬렉션 필드(memberRoles, memberLikes)는 Collections.unmodifiableList로 감싸서 반환해야 하므로,
    // 필드별로 세밀하게 제어하기 위해 수동으로 Getter를 작성했다.

    /** 회원의 고유 식별자(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 회원의 실명을 반환한다. */
    public String getName() {
        return name;
    }

    /** 회원의 닉네임을 반환한다. */
    public String getNickname() {
        return nickname;
    }

    /** 회원의 이메일을 반환한다. */
    public String getEmail() {
        return email;
    }

    /** 회원의 BCrypt 해시된 비밀번호를 반환한다. (8주차 추가) */
    public String getPassword() {
        return password;
    }

    /** 회원의 성별을 반환한다. */
    public Gender getGender() {
        return gender;
    }

    /** 회원의 생년월일을 반환한다. */
    public LocalDate getBirth() {
        return birth;
    }

    /** 회원의 전화번호를 반환한다. */
    public String getPhoneNum() {
        return phoneNum;
    }

    /** 회원의 주소를 반환한다. */
    public String getAddress() {
        return address;
    }

    /** 회원의 보유 포인트를 반환한다. */
    public Long getPoint() {
        return point;
    }

    /** 회원이 완료한 미션 수를 반환한다. */
    public Integer getMissionClear() {
        return missionClear;
    }

    /**
     * 회원의 역할 목록을 읽기 전용(unmodifiable)으로 반환한다.
     *
     * <p>왜 Collections.unmodifiableList를 쓰는가?</p>
     * <p>만약 원본 리스트를 그대로 반환하면, 외부 코드에서 getMemberRoles().add(...)나
     * getMemberRoles().remove(...)를 호출하여 엔티티의 내부 상태를 마음대로 바꿀 수 있다.
     * 이는 "캡슐화(encapsulation)" 원칙을 위반하며, 예기치 않은 버그를 유발한다.</p>
     * <p>unmodifiableList로 감싸면, add/remove 호출 시 UnsupportedOperationException이 발생하여
     * 외부에서의 무단 수정을 원천 차단한다.</p>
     */
    public List<MemberRole> getMemberRoles() {
        return Collections.unmodifiableList(memberRoles);
    }

    /**
     * 회원의 음식 취향 목록을 읽기 전용(unmodifiable)으로 반환한다.
     * getMemberRoles()와 동일한 이유로 unmodifiableList를 사용한다.
     */
    public List<MemberLikes> getMemberLikes() {
        return Collections.unmodifiableList(memberLikes);
    }

    /** 회원이 작성한 리뷰 목록을 읽기 전용으로 반환한다. */
    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /** 회원의 미션 참여 기록 목록을 읽기 전용으로 반환한다. */
    public List<MemberMission> getMemberMissions() {
        return Collections.unmodifiableList(memberMissions);
    }
}
