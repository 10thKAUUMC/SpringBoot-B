# UMC 10기 4주차 미션: ERD 기반 도메인형 아키텍처 Spring 프로젝트 세팅

> 0주차에 작성한 ERD(Entity Relationship Diagram)를 바탕으로, 실제 Spring Boot 프로젝트를 만들고 JPA Entity를 구현하는 미션입니다.

---

## 목차

1. [사전 지식 — 이 프로젝트를 이해하기 위해 알아야 할 것들](#1-사전-지식--이-프로젝트를-이해하기-위해-알아야-할-것들)
2. [기술 스택](#2-기술-스택)
3. [프로젝트 구조 — 도메인형 아키텍처란?](#3-프로젝트-구조--도메인형-아키텍처란)
4. [ERD (Entity Relationship Diagram)](#4-erd-entity-relationship-diagram)
5. [Entity 상세 설명 — JPA가 뭐야?](#5-entity-상세-설명--jpa가-뭐야)
6. [어노테이션(Annotation) 사전](#6-어노테이션annotation-사전)
7. [Enum — 왜 문자열 대신 Enum을 쓸까?](#7-enum--왜-문자열-대신-enum을-쓸까)
8. [BaseEntity — 코드 중복을 줄이는 상속](#8-baseentity--코드-중복을-줄이는-상속)
9. [연관관계 매핑 — 테이블끼리 어떻게 연결할까?](#9-연관관계-매핑--테이블끼리-어떻게-연결할까)
10. [Swagger — API 문서 자동화](#10-swagger--api-문서-자동화)
11. [테스트 코드 — 내가 짠 코드가 제대로 동작하는지 확인하기](#11-테스트-코드--내가-짠-코드가-제대로-동작하는지-확인하기)
12. [셀프 피드백 — 0주차 ERD에서 개선한 점들](#12-셀프-피드백--0주차-erd에서-개선한-점들)
13. [프로젝트 실행 방법](#13-프로젝트-실행-방법)

---

## 1. 사전 지식 — 이 프로젝트를 이해하기 위해 알아야 할 것들

### Spring Boot가 뭐야?

**Spring**은 Java로 웹 서버(백엔드)를 만들 때 사용하는 **프레임워크**입니다.
프레임워크란? 개발할 때 "이런 구조로 만들어"라고 뼈대를 잡아주는 도구라고 생각하면 됩니다.

**Spring Boot**는 Spring을 더 쉽게 쓸 수 있게 만든 버전입니다.
원래 Spring은 설정할 게 엄청 많았는데, Spring Boot는 "자주 쓰는 설정은 내가 알아서 해줄게"라는 철학으로 만들어졌습니다.

### Gradle이 뭐야?

Gradle은 **빌드 도구**입니다. 우리가 작성한 Java 코드를 컴퓨터가 이해할 수 있는 형태로 변환(컴파일)하고, 외부 라이브러리를 자동으로 다운로드해주는 역할을 합니다.

`build.gradle` 파일에 "이 라이브러리가 필요해"라고 적으면, Gradle이 알아서 인터넷에서 다운받아줍니다. pip(Python)이나 npm(JavaScript)과 비슷한 역할이에요.

### JPA가 뭐야?

**JPA(Java Persistence API)**는 Java 코드로 데이터베이스를 다룰 수 있게 해주는 기술입니다.

원래 데이터베이스에 데이터를 저장하려면 SQL이라는 별도의 언어를 써야 합니다:
```sql
INSERT INTO member (name, email) VALUES ('유완규', 'test@test.com');
```

JPA를 쓰면 Java 코드만으로 같은 일을 할 수 있습니다:
```java
Member member = Member.builder().name("유완규").email("test@test.com").build();
memberRepository.save(member);  // 이 한 줄이 위의 SQL과 같은 일을 합니다!
```

JPA는 Java 클래스(Entity)와 데이터베이스 테이블을 **자동으로 연결(매핑)**해줍니다. 이것을 **ORM(Object-Relational Mapping)**이라고 부릅니다.

### Entity가 뭐야?

Entity는 **데이터베이스의 테이블 하나를 Java 클래스로 표현한 것**입니다.

예를 들어, 데이터베이스에 `member`라는 테이블이 있으면:

| id | name | email | gender |
|----|------|-------|--------|
| 1 | 유완규 | test@test.com | MALE |

이 테이블을 Java 클래스로 표현하면:
```java
@Entity              // "이 클래스는 DB 테이블이야"
@Table(name = "member")  // "테이블 이름은 member야"
public class Member {
    private Long id;      // 테이블의 id 컬럼
    private String name;  // 테이블의 name 컬럼
    private String email; // 테이블의 email 컬럼
    private Gender gender;// 테이블의 gender 컬럼
}
```

### Repository가 뭐야?

Repository는 **데이터베이스에 접근하는 통로**입니다.

Entity가 "데이터의 모양"을 정의한다면, Repository는 "그 데이터를 어떻게 저장하고 꺼내올지"를 담당합니다.

```java
public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

이 한 줄만 작성하면, Spring이 알아서 아래 기능들을 만들어줍니다:
- `save()` — 데이터 저장
- `findById()` — ID로 데이터 하나 조회
- `findAll()` — 전체 데이터 조회
- `delete()` — 데이터 삭제
- 등등...

---

## 2. 기술 스택

| 기술 | 버전 | 설명 |
|------|------|------|
| **Java** | 21 (LTS) | 프로그래밍 언어. LTS는 Long-Term Support의 약자로, 오랫동안 업데이트를 지원해주는 안정적인 버전이라는 뜻 |
| **Spring Boot** | 3.4.4 | Java 웹 프레임워크 |
| **Gradle** | 8.10.2 | 빌드 도구 (라이브러리 관리 + 코드 컴파일) |
| **Spring Data JPA** | - | 데이터베이스를 Java 코드로 다루는 기술 |
| **MySQL** | - | 실제 운영할 때 사용할 데이터베이스 |
| **H2** | - | 테스트할 때 사용하는 가벼운 인메모리 데이터베이스 |
| **Lombok** | - | 반복적인 코드(getter, setter, 생성자 등)를 자동 생성해주는 라이브러리 |
| **Swagger (springdoc)** | 2.8.6 | API 문서를 자동으로 만들어주는 도구 |

### build.gradle 상세 설명

```groovy
plugins {
    id 'java'                                              // Java 프로젝트라는 선언
    id 'org.springframework.boot' version '3.4.4'          // Spring Boot 사용
    id 'io.spring.dependency-management' version '1.1.7'   // 라이브러리 버전 자동 관리
}

group = 'umc.study'          // 프로젝트 그룹명 (보통 회사/조직의 도메인을 뒤집어서 씀)
version = '0.0.1-SNAPSHOT'   // 프로젝트 버전. SNAPSHOT은 "아직 개발 중"이라는 의미

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)  // Java 21 버전 사용
    }
}

dependencies {
    // === Spring 관련 ===
    implementation 'org.springframework.boot:spring-boot-starter-web'        // 웹 서버 기능 (HTTP 요청 처리)
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'   // JPA (DB 연동)
    implementation 'org.springframework.boot:spring-boot-starter-validation' // 입력값 검증 (@NotNull, @Email 등)

    // === 데이터베이스 ===
    runtimeOnly 'com.mysql:mysql-connector-j'  // MySQL 드라이버 (Java와 MySQL을 연결해주는 다리)

    // === Lombok (코드 자동 생성) ===
    compileOnly 'org.projectlombok:lombok'           // 컴파일 시에만 사용
    annotationProcessor 'org.projectlombok:lombok'   // 어노테이션 처리기

    // === Swagger (API 문서) ===
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6'

    // === 테스트 ===
    testImplementation 'org.springframework.boot:spring-boot-starter-test'  // 테스트 프레임워크
    testRuntimeOnly 'com.h2database:h2'                                     // 테스트용 인메모리 DB
    testCompileOnly 'org.projectlombok:lombok'
    testAnnotationProcessor 'org.projectlombok:lombok'
}
```

**`implementation` vs `compileOnly` vs `runtimeOnly` 차이:**
- `implementation`: 코드 작성할 때도, 실행할 때도 필요한 라이브러리
- `compileOnly`: 코드 작성(컴파일)할 때만 필요. 실행할 때는 필요 없음 (Lombok은 컴파일 시점에 코드를 생성해주고 나면 역할 끝)
- `runtimeOnly`: 실행할 때만 필요. 코드에서 직접 사용하진 않지만 실행 시 내부적으로 필요 (DB 드라이버가 대표적)

---

## 3. 프로젝트 구조 — 도메인형 아키텍처란?

```
src/main/java/umc/study/umc_mission/
├── UmcMissionApplication.java          ← 앱 시작점 (main 메서드)
│
├── domain/                              ← 비즈니스 로직을 "도메인"별로 분류
│   ├── alarm/                           ← 알림 도메인
│   │   ├── entity/
│   │   │   └── Alarm.java               ← 알림 테이블
│   │   ├── enums/
│   │   │   └── AlarmType.java           ← 알림 종류 (MISSION, REVIEW, POINT, SYSTEM)
│   │   └── repository/
│   │       └── AlarmRepository.java     ← 알림 DB 접근
│   │
│   ├── member/                          ← 회원 도메인
│   │   ├── entity/
│   │   │   ├── Member.java              ← 회원 테이블
│   │   │   ├── MemberLikes.java         ← 회원-음식취향 중간 테이블
│   │   │   ├── MemberRole.java          ← 회원-역할 중간 테이블
│   │   │   └── Role.java                ← 역할 테이블 (USER, ADMIN 등)
│   │   ├── enums/
│   │   │   └── Gender.java              ← 성별 (MALE, FEMALE, OTHER)
│   │   └── repository/
│   │       └── MemberRepository.java
│   │
│   ├── mission/                         ← 미션 도메인
│   │   ├── entity/
│   │   │   ├── Mission.java             ← 미션 테이블
│   │   │   ├── MemberMission.java       ← 회원-미션 중간 테이블
│   │   │   └── PointHistory.java        ← 포인트 이력 테이블
│   │   ├── enums/
│   │   │   ├── MissionState.java        ← 미션 상태 (CHALLENGING, COMPLETED)
│   │   │   ├── MissionType.java         ← 미션 종류 (VISIT, REVIEW, PURCHASE, EVENT)
│   │   │   └── PointType.java           ← 포인트 종류 (EARN, USE)
│   │   └── repository/
│   │       └── MissionRepository.java
│   │
│   ├── review/                          ← 리뷰 도메인
│   │   ├── entity/
│   │   │   └── Review.java              ← 리뷰 테이블
│   │   └── repository/
│   │       └── ReviewRepository.java
│   │
│   └── store/                           ← 가게 도메인
│       ├── entity/
│       │   ├── Store.java               ← 가게 테이블
│       │   ├── Region.java              ← 지역 테이블
│       │   └── FoodKind.java            ← 음식 종류 테이블
│       └── repository/
│           └── StoreRepository.java
│
└── global/                              ← 프로젝트 전체에서 공통으로 쓰는 코드
    ├── common/
    │   └── BaseEntity.java              ← 모든 Entity가 공통으로 가지는 필드 (createdAt, updatedAt)
    ├── config/
    │   ├── JpaAuditingConfig.java       ← JPA Auditing 설정 (자동 시간 기록)
    │   └── SwaggerConfig.java           ← Swagger(API 문서) 설정
    └── exception/                       ← (아직 비어있음) 에러 처리 코드가 들어갈 곳
```

### "도메인형" vs "계층형" — 뭐가 다른데?

**계층형 아키텍처** (흔히 쓰지만 규모가 커지면 불편):
```
src/
├── entity/          ← 모든 Entity가 한 폴더에!
│   ├── Member.java
│   ├── Store.java
│   ├── Mission.java
│   └── ... (20개가 넘으면 찾기 힘듦)
├── repository/
├── service/
└── controller/
```

**도메인형 아키텍처** (이 프로젝트가 사용하는 방식):
```
src/
├── domain/
│   ├── member/      ← 회원 관련 코드는 여기에 다 모여있음!
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/     (나중에 추가)
│   │   └── controller/  (나중에 추가)
│   ├── store/       ← 가게 관련 코드는 여기에!
│   └── ...
└── global/          ← 공통 코드
```

**도메인형의 장점:**
- "회원 관련 코드를 수정해야 해" → `member/` 폴더만 보면 됨
- 기능별로 정리되어 있어서 찾기 쉬움
- 나중에 프로젝트가 커져도 구조가 깔끔하게 유지됨

---

## 4. ERD (Entity Relationship Diagram)

ERD는 **데이터베이스 테이블 간의 관계를 그림으로 표현한 것**입니다.

이 프로젝트에는 총 **12개의 테이블**이 있습니다:

```
┌──────────┐     ┌─────────────┐     ┌──────────┐
│  Region  │────<│    Store     │>────│ FoodKind │
│ (지역)    │     │   (가게)     │     │ (음식종류) │
└──────────┘     └──────┬──────┘     └────┬─────┘
                        │                  │
              ┌─────────┤                  │
              │         │                  │
        ┌─────┴────┐  ┌─┴───────┐   ┌─────┴───────┐
        │  Review   │  │ Mission │   │ MemberLikes │
        │  (리뷰)   │  │ (미션)   │   │ (음식취향)   │
        └─────┬────┘  └────┬────┘   └──────┬──────┘
              │             │               │
              │       ┌─────┴──────┐        │
              │       │MemberMission│        │
              │       │(회원-미션)   │        │
              │       └─────┬──────┘        │
              │             │               │
        ┌─────┴─────────────┴───────────────┴──┐
        │                Member                 │
        │               (회원)                   │
        └───────┬──────────────────────┬───────┘
                │                      │
         ┌──────┴─────┐         ┌──────┴──────┐
         │ MemberRole │         │    Alarm    │
         │ (회원역할)   │         │   (알림)     │
         └──────┬─────┘         └─────────────┘
                │
         ┌──────┴──────┐     ┌──────────────┐
         │    Role     │     │ PointHistory │
         │   (역할)    │     │  (포인트이력)  │
         └─────────────┘     └──────────────┘
```

**테이블 간 관계 설명:**
- 하나의 **Region**(지역)에는 여러 개의 **Store**(가게)가 있음 (1:N)
- 하나의 **Store**에는 여러 개의 **Mission**(미션)과 **Review**(리뷰)가 있음 (1:N)
- 하나의 **Member**(회원)는 여러 개의 리뷰를 작성하고, 여러 미션에 참여할 수 있음 (1:N)
- **MemberMission**은 회원과 미션의 **다대다(N:M) 관계**를 풀어주는 중간 테이블
- **MemberLikes**는 회원과 음식종류의 다대다 관계를 풀어주는 중간 테이블
- **MemberRole**은 회원과 역할의 다대다 관계를 풀어주는 중간 테이블

---

## 5. Entity 상세 설명 — JPA가 뭐야?

### 5.1 Member (회원)

```java
@Entity                                          // ① "이 클래스는 DB 테이블과 매핑되는 Entity야"
@Table(name = "member")                          // ② 테이블 이름을 "member"로 지정
@Getter                                          // ③ Lombok: 모든 필드의 getter 메서드를 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ④ 아래에서 자세히 설명
@AllArgsConstructor                              // ⑤ 모든 필드를 받는 생성자 자동 생성
@Builder                                         // ⑥ Builder 패턴 사용 가능 (아래에서 자세히 설명)
public class Member extends BaseEntity {         // ⑦ BaseEntity를 상속 → createdAt, updatedAt 자동 포함

    @Id                                          // ⑧ 이 필드가 테이블의 기본 키(Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ⑨ 값을 자동으로 1, 2, 3... 증가시킴 (AUTO_INCREMENT)
    private Long id;

    @Column(nullable = false, length = 10)       // ⑩ NOT NULL, 최대 10글자
    private String name;

    @Column(length = 20)
    private String nickname;                     // nullable = true (기본값) → NULL 허용

    @Column(nullable = false, length = 30)
    private String email;

    @Enumerated(EnumType.STRING)                 // ⑪ Enum을 문자열("MALE")로 DB에 저장
    @Column(length = 10)
    private Gender gender;

    private LocalDate birth;                     // 생년월일 (DATE 타입)

    @Column(length = 15)
    private String phoneNum;

    @Column(length = 50)
    private String address;

    @Builder.Default                             // ⑫ Builder로 객체 생성 시 기본값 지정
    @Column(nullable = false)
    private Long point = 0L;                     // 기본값 0

    @Builder.Default
    @Column(nullable = false)
    private Integer missionClear = 0;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default                             // ⑬ 아래 연관관계 섹션에서 자세히 설명
    private List<MemberRole> memberRoles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberLikes> memberLikes = new ArrayList<>();
}
```

**④ `@NoArgsConstructor(access = AccessLevel.PROTECTED)` — 왜 PROTECTED?**

JPA는 내부적으로 Entity 객체를 만들 때 **기본 생성자**(파라미터가 없는 생성자)가 필요합니다.
하지만 아무나 `new Member()`로 빈 객체를 만들면 필수 데이터(name, email 등)가 빠진 불완전한 객체가 생길 수 있습니다.

`PROTECTED`로 설정하면:
- JPA는 사용 가능 (JPA는 같은 패키지 또는 상속 관계에서 접근하므로 PROTECTED면 충분)
- 외부 코드에서 `new Member()`로 직접 생성하는 것은 막힘
- 대신 `Member.builder().name("...").email("...").build()` 처럼 Builder를 통해 만들도록 유도

**⑥ Builder 패턴 — 왜 `new Member(이름, 닉네임, 이메일, ...)`보다 좋을까?**

```java
// ❌ 생성자 방식 — 파라미터 순서를 외워야 하고, 실수하기 쉬움
Member member = new Member("유완규", "wangyu", "test@test.com", Gender.MALE, ...);

// ✅ Builder 방식 — 어떤 값을 넣는지 명확하게 보임
Member member = Member.builder()
    .name("유완규")
    .nickname("wangyu")
    .email("test@test.com")
    .gender(Gender.MALE)
    .build();
```

Builder 패턴의 장점:
1. **가독성**: 어떤 필드에 어떤 값을 넣는지 한눈에 보임
2. **선택적 설정**: 필요한 필드만 설정 가능 (나머지는 null 또는 기본값)
3. **실수 방지**: 파라미터 순서를 헷갈릴 일이 없음

### 5.2 Store (가게)

```java
@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)            // 여러 가게가 하나의 지역에 속함
    @JoinColumn(name = "region_id", nullable = false) // FK(외래 키) 컬럼명: region_id
    private Region region;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 10)
    private String type;

    @Column(length = 30)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isOpened = false;             // 영업 중 여부 (기본값: false)

    private LocalTime openTime;                   // 영업 시작 시간 (예: 09:00)
    private LocalTime closeTime;                  // 영업 종료 시간 (예: 22:00)
}
```

**`LocalTime`을 쓴 이유:** 영업 시간은 "09:00", "22:00"처럼 **시간**만 필요합니다. 날짜는 필요 없으므로 `LocalDateTime`(날짜+시간)이 아닌 `LocalTime`(시간만)을 사용합니다.

### 5.3 Mission (미션)

```java
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;                          // 이 미션이 어떤 가게에서 진행되는지

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private MissionType type;                     // VISIT(방문), REVIEW(리뷰), PURCHASE(구매), EVENT(이벤트)

    @Column(nullable = false, length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")            // TEXT 타입 = 매우 긴 문자열 저장 가능
    private String content;

    @Column(nullable = false)
    private Long reward;                          // 보상 포인트

    private LocalDateTime expiredAt;              // 미션 만료 시간
}
```

### 5.4 MemberMission (회원-미션 중간 테이블)

```java
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private MissionState state = MissionState.CHALLENGING; // 기본값: 도전 중
}
```

**왜 중간 테이블이 필요할까?**

한 회원이 여러 미션에 참여할 수 있고, 하나의 미션에 여러 회원이 참여할 수 있습니다.
이런 **다대다(N:M) 관계**는 데이터베이스에서 직접 표현할 수 없기 때문에, 중간 테이블(`MemberMission`)을 만들어서 두 개의 **다대일(N:1) 관계**로 풀어줍니다.

```
Member (1) ──< MemberMission >── (1) Mission
         N                    N
```

추가로 이 중간 테이블에 `state`(미션 상태) 같은 **관계 자체의 속성**도 넣을 수 있다는 장점이 있습니다.

### 5.5 PointHistory (포인트 이력)

```java
@Entity
@Table(name = "point_history")
@EntityListeners(AuditingEntityListener.class)   // JPA Auditing 사용 (자동 시간 기록)
public class PointHistory {
    // BaseEntity를 상속하지 않음 → updatedAt이 필요 없기 때문
    // 포인트 이력은 한번 기록되면 수정되지 않음 (INSERT only)

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;             // 생성 시간만 기록, 수정 불가
}
```

### 5.6 Review (리뷰)

```java
public class Review extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;      // 리뷰를 작성한 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;        // 리뷰 대상 가게

    @Column(nullable = false, length = 1)
    private String rating;      // 별점 ("1" ~ "5")

    @Column(columnDefinition = "TEXT")
    private String content;     // 리뷰 내용
}
```

### 5.7 Alarm (알림)

```java
public class Alarm extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isConfirmed = false;  // 알림 확인 여부 (기본값: 미확인)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AlarmType type;               // MISSION, REVIEW, POINT, SYSTEM

    @Column(nullable = false, length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
}
```

---

## 6. 어노테이션(Annotation) 사전

Java에서 `@`로 시작하는 것들을 **어노테이션**이라고 합니다. "이 코드에 이런 기능을 붙여줘"라는 메타데이터(설명서)입니다.

### JPA 관련

| 어노테이션 | 의미 | 쉬운 설명 |
|-----------|------|----------|
| `@Entity` | 이 클래스는 DB 테이블이다 | "이 클래스를 테이블로 만들어줘" |
| `@Table(name = "xxx")` | 테이블 이름 지정 | 안 쓰면 클래스 이름이 테이블 이름이 됨 |
| `@Id` | 기본 키(Primary Key) | 이 필드가 각 행(row)을 구별하는 고유한 값 |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | 자동 증가 | INSERT할 때 1, 2, 3... 자동으로 번호 매김 (MySQL AUTO_INCREMENT) |
| `@Column(nullable, length, ...)` | 컬럼 설정 | nullable=false → NOT NULL, length=10 → VARCHAR(10) |
| `@Column(columnDefinition = "TEXT")` | 컬럼 타입 직접 지정 | 긴 문자열을 저장할 때 (VARCHAR는 최대 255자) |
| `@Column(updatable = false)` | 수정 불가 | 한번 저장된 후 UPDATE 쿼리에서 제외됨 |
| `@Enumerated(EnumType.STRING)` | Enum을 문자열로 저장 | MALE은 DB에 "MALE"로 저장됨 (ORDINAL이면 0, 1, 2 숫자로 저장 — 위험!) |
| `@ManyToOne` | 다대일 관계 | "여러 개의 나"가 "하나의 상대"에 연결됨 |
| `@OneToMany` | 일대다 관계 | "하나의 나"에 "여러 개의 상대"가 연결됨 |
| `@JoinColumn(name = "xxx_id")` | 외래 키 컬럼명 지정 | 이 필드가 DB에서 어떤 컬럼명으로 저장될지 |
| `@MappedSuperclass` | 테이블 생성 X, 상속만 | "나는 테이블이 아니라 다른 Entity에게 필드를 물려주는 부모야" |
| `@EntityListeners(AuditingEntityListener.class)` | 자동 시간 기록 활성화 | `@CreatedDate`, `@LastModifiedDate`가 동작하게 해줌 |

### Lombok 관련

| 어노테이션 | 자동 생성되는 코드 | 쉬운 설명 |
|-----------|-------------------|----------|
| `@Getter` | `public String getName() { return name; }` | 모든 필드의 getter 메서드 |
| `@NoArgsConstructor` | `public Member() {}` | 파라미터 없는 생성자 |
| `@AllArgsConstructor` | `public Member(Long id, String name, ...)` | 모든 필드를 받는 생성자 |
| `@Builder` | `Member.builder().name("...").build()` | Builder 패턴 사용 가능 |
| `@Builder.Default` | (빌더에서 기본값 유지) | Builder로 만들 때 이 필드를 안 넣으면 지정한 기본값 사용 |

### Spring 관련

| 어노테이션 | 의미 | 쉬운 설명 |
|-----------|------|----------|
| `@SpringBootApplication` | Spring Boot 앱의 시작점 | 이 클래스가 앱의 "진입문" |
| `@Configuration` | 설정 클래스 | "이 클래스는 앱 설정을 담당해" |
| `@EnableJpaAuditing` | JPA Auditing 활성화 | createdAt/updatedAt 자동 기록 기능을 켜줌 |
| `@Bean` | 스프링이 관리하는 객체 등록 | "이 메서드가 반환하는 객체를 스프링이 관리해줘" |
| `@CreatedDate` | 생성 시간 자동 기록 | INSERT할 때 자동으로 현재 시간이 들어감 |
| `@LastModifiedDate` | 수정 시간 자동 기록 | UPDATE할 때 자동으로 현재 시간이 들어감 |

---

## 7. Enum — 왜 문자열 대신 Enum을 쓸까?

### Enum이 뭐야?

Enum(열거형)은 **정해진 값들만 사용할 수 있도록 제한하는 타입**입니다.

```java
public enum Gender {
    MALE, FEMALE, OTHER  // 이 3개 값만 사용 가능
}
```

### 문자열(String) vs Enum 비교

```java
// ❌ String을 쓰면 — 아무 문자열이나 넣을 수 있어서 위험
member.setGender("male");     // 소문자
member.setGender("Male");     // 대문자 혼합
member.setGender("남성");     // 한글
member.setGender("M");        // 약자
member.setGender("맥주");     // 완전히 엉뚱한 값도 들어감!

// ✅ Enum을 쓰면 — 정해진 값만 넣을 수 있음
member.setGender(Gender.MALE);    // 컴파일러가 "MALE, FEMALE, OTHER 중 하나만 써!"라고 체크
member.setGender(Gender.맥주);    // ❌ 컴파일 에러! (이런 값은 없으니까)
```

### 이 프로젝트의 Enum 목록

| Enum | 값들 | 용도 |
|------|------|------|
| `Gender` | MALE, FEMALE, OTHER | 회원 성별 |
| `AlarmType` | MISSION, REVIEW, POINT, SYSTEM | 알림 종류 |
| `MissionType` | VISIT, REVIEW, PURCHASE, EVENT | 미션 종류 |
| `MissionState` | CHALLENGING, COMPLETED | 미션 진행 상태 |
| `PointType` | EARN, USE | 포인트 적립/사용 |

### `EnumType.STRING` vs `EnumType.ORDINAL` — 왜 STRING을 써야 할까?

```java
@Enumerated(EnumType.STRING)   // ✅ "MALE" 이라는 문자열이 DB에 저장됨
@Enumerated(EnumType.ORDINAL)  // ❌ 0, 1, 2 같은 숫자가 DB에 저장됨
```

ORDINAL이 위험한 이유:
```java
// 처음: MALE=0, FEMALE=1, OTHER=2
public enum Gender { MALE, FEMALE, OTHER }

// 나중에 새 값을 추가하면:
public enum Gender { MALE, NON_BINARY, FEMALE, OTHER }
// NON_BINARY=1이 되면서, 기존에 FEMALE이었던 데이터(1)가 NON_BINARY로 바뀌어버림!
```

---

## 8. BaseEntity — 코드 중복을 줄이는 상속

### 문제: 모든 테이블에 createdAt, updatedAt이 있음

```java
// ❌ 중복 코드 — 모든 Entity에 같은 필드를 반복 작성해야 함
public class Member {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

public class Store {
    private LocalDateTime createdAt;  // 또 같은 코드...
    private LocalDateTime updatedAt;
}
```

### 해결: BaseEntity를 만들어서 상속

```java
// ✅ 공통 필드를 부모 클래스에 한 번만 작성
@Getter
@MappedSuperclass                               // "나는 테이블이 아니야, 필드만 물려주는 부모야"
@EntityListeners(AuditingEntityListener.class)   // 자동 시간 기록 기능 활성화
public abstract class BaseEntity {

    @CreatedDate                // INSERT 시 자동으로 현재 시간 저장
    @Column(updatable = false)  // 한번 저장되면 수정 불가
    private LocalDateTime createdAt;

    @LastModifiedDate           // UPDATE 시 자동으로 현재 시간 갱신
    private LocalDateTime updatedAt;
}

// 이제 다른 Entity들은 상속만 하면 됨!
public class Member extends BaseEntity { ... }
public class Store extends BaseEntity { ... }
```

**`@MappedSuperclass`가 없으면?**
JPA가 BaseEntity도 테이블로 만들려고 시도합니다. 하지만 BaseEntity는 그 자체로 테이블이 될 필요가 없고, 다른 Entity에게 필드를 물려주는 역할만 하면 됩니다.

**JPA Auditing이란?**
"누가, 언제 이 데이터를 만들고 수정했는지"를 자동으로 기록해주는 기능입니다.
`@EnableJpaAuditing` 설정을 켜면, `@CreatedDate`와 `@LastModifiedDate`가 자동으로 동작합니다.

---

## 9. 연관관계 매핑 — 테이블끼리 어떻게 연결할까?

### FetchType.LAZY vs FetchType.EAGER

```java
@ManyToOne(fetch = FetchType.LAZY)   // ✅ 이 프로젝트에서 사용하는 방식
@ManyToOne(fetch = FetchType.EAGER)  // ❌ 기본값이지만 성능 문제 발생 가능
```

**EAGER(즉시 로딩):** Store를 조회하면, 연결된 Region도 **무조건 같이** 조회합니다.
```sql
-- Store만 필요한데도 Region까지 JOIN해서 가져옴
SELECT s.*, r.* FROM store s JOIN region r ON s.region_id = r.id WHERE s.id = 1;
```

**LAZY(지연 로딩):** Store를 조회할 때는 Store만 가져오고, Region은 **실제로 사용할 때** 가져옵니다.
```sql
-- 1단계: Store만 조회
SELECT * FROM store WHERE id = 1;

-- 2단계: store.getRegion().getName()을 호출할 때 비로소 Region 조회
SELECT * FROM region WHERE id = 3;
```

**왜 LAZY가 좋을까?**

예를 들어 Store 목록 100개를 조회하는데, Region 정보는 안 쓴다면:
- EAGER: 100개의 Store + 100개의 Region을 전부 조회 (불필요한 쿼리 100개 추가)
- LAZY: 100개의 Store만 조회 (Region은 필요할 때만 조회)

이 불필요한 추가 쿼리 문제를 **N+1 문제**라고 부릅니다. LAZY를 기본으로 쓰면 이 문제를 예방할 수 있습니다.

### cascade와 orphanRemoval

```java
@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
private List<MemberRole> memberRoles = new ArrayList<>();
```

**`cascade = CascadeType.ALL`** — 부모(Member)에 대한 작업이 자식(MemberRole)에게 전파됨
- Member를 저장하면 → 연결된 MemberRole도 자동으로 저장됨
- Member를 삭제하면 → 연결된 MemberRole도 자동으로 삭제됨

**`orphanRemoval = true`** — 고아 객체 자동 삭제
- `member.getMemberRoles().remove(0)` 처럼 리스트에서 빼면 → DB에서도 자동 삭제됨

### mappedBy — 연관관계의 주인

```java
// Member.java (주인이 아닌 쪽)
@OneToMany(mappedBy = "member")  // "MemberRole의 member 필드가 이 관계를 관리해"
private List<MemberRole> memberRoles;

// MemberRole.java (연관관계의 주인 = 외래 키를 가진 쪽)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "member_id")  // 실제 DB에 member_id 컬럼이 생기는 곳
private Member member;
```

**연관관계의 주인**: DB에서 **외래 키(Foreign Key)**를 실제로 가지고 있는 쪽입니다.
- `member_role` 테이블에 `member_id` 컬럼이 있으므로 → MemberRole이 주인
- `mappedBy`는 "나는 주인이 아니야, 저쪽이 주인이야"라고 알려주는 것

---

## 10. Swagger — API 문서 자동화

### Swagger가 뭐야?

Swagger(스웨거)는 **REST API의 문서를 자동으로 만들어주는 도구**입니다.

백엔드 개발자가 API를 만들면, 프론트엔드 개발자는 "이 API의 URL이 뭐야? 어떤 데이터를 보내야 해?"를 알아야 합니다. 이걸 일일이 문서로 적는 대신, Swagger가 코드를 분석해서 자동으로 만들어줍니다.

### 접속 방법

서버를 실행한 후 브라우저에서: `http://localhost:8080/swagger-ui/index.html`

### SwaggerConfig 설명

```java
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        // API 기본 정보 설정
        Info info = new Info()
                .title("UMC Mission")
                .description("UMC 10기 미션 API")
                .version("0.0.1");

        // JWT 인증 설정 (나중에 로그인 기능 추가 시 사용)
        String securityScheme = "JWT TOKEN";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityScheme);

        Components components = new Components()
                .addSecuritySchemes(securityScheme, new SecurityScheme()
                        .name(securityScheme)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")              // "Bearer xxxxx" 형식의 토큰
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))  // API 기본 URL
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
```

---

## 11. 테스트 코드 — 내가 짠 코드가 제대로 동작하는지 확인하기

### 테스트를 왜 해야 할까?

코드를 짠 후 "잘 돌아가겠지~"라고 넘어가면, 나중에 문제가 생겼을 때 원인을 찾기 어렵습니다.
테스트 코드는 **"이 코드가 이렇게 동작해야 한다"는 것을 미리 확인하는 자동 검증 장치**입니다.

### H2 인메모리 데이터베이스

테스트할 때 실제 MySQL을 쓰면:
- MySQL을 설치하고 실행해야 함
- 테스트 데이터가 실제 DB에 쌓임
- 다른 사람의 PC에서 실행 안 될 수 있음

**H2 인메모리 DB**는:
- 설치 불필요 (라이브러리만 추가하면 됨)
- **메모리에서만 동작** — 테스트가 끝나면 자동으로 사라짐
- `ddl-auto: create-drop` — 테스트 시작 시 테이블 생성, 종료 시 삭제

### application-test.yml (테스트 전용 설정)

```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver            # H2 데이터베이스 사용
    url: jdbc:h2:mem:testdb;MODE=MySQL           # 메모리에서 동작, MySQL 호환 모드
    username: sa
    password:                                     # 비밀번호 없음

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop                       # 테스트 시작: 테이블 생성 → 종료: 삭제
    show-sql: true                                # 실행되는 SQL을 콘솔에 출력 (디버깅용)
```

### 테스트 코드 예시: MemberRepositoryTest

```java
@DataJpaTest         // JPA 관련 컴포넌트만 로딩 (전체 앱을 띄우지 않아서 빠름)
@ActiveProfiles("test")  // application-test.yml 설정 사용
class MemberRepositoryTest {

    @Autowired                       // Spring이 자동으로 MemberRepository 객체를 넣어줌
    private MemberRepository memberRepository;

    @Test                            // "이 메서드는 테스트야"
    @DisplayName("회원을 저장하고 조회할 수 있다")  // 테스트 이름 (보고서에 표시됨)
    void saveMember() {
        // given — 테스트할 데이터 준비
        Member member = Member.builder()
                .name("유완규")
                .nickname("wangyu")
                .email("wangyu@test.com")
                .gender(Gender.MALE)
                .birth(LocalDate.of(2002, 1, 1))
                .phoneNum("0101234567")
                .address("서울특별시")
                .build();

        // when — 테스트할 동작 실행
        Member saved = memberRepository.save(member);

        // then — 결과 검증 ("이래야 한다!")
        assertThat(saved.getId()).isNotNull();           // ID가 자동 생성되었는지
        assertThat(saved.getName()).isEqualTo("유완규");   // 이름이 제대로 저장되었는지
        assertThat(saved.getPoint()).isEqualTo(0L);      // 기본값이 0인지
        assertThat(saved.getMissionClear()).isEqualTo(0); // 기본값이 0인지
    }
}
```

**Given-When-Then 패턴:**
- **Given** (준비): 테스트에 필요한 데이터를 만듦
- **When** (실행): 테스트하고 싶은 동작을 실행
- **Then** (검증): 결과가 예상대로인지 확인

### 테스트 실행 방법

```bash
./gradlew test
```

---

## 12. 셀프 피드백 — 0주차 ERD에서 개선한 점들

| 개선 전 (0주차 ERD) | 개선 후 (이번 구현) | 왜 개선했는지 |
|-------------------|-------------------|-------------|
| `gender VARCHAR(10)` | `Gender` **Enum** | 잘못된 값("맥주" 등)이 들어가는 것을 방지. 코드에서 타입 안전성 확보 |
| `mission_type VARCHAR(15)` | `MissionType` **Enum** | 같은 이유 |
| `is_opened TIMESTAMP` | `Boolean isOpened` | "영업 중인지 여부"는 시간이 아니라 true/false가 맞음 |
| `open_time DATETIME` | `LocalTime openTime` | 영업 시간은 "09:00"처럼 시간만 필요. 날짜는 불필요 |
| 각 Entity에 createdAt/updatedAt 중복 작성 | `BaseEntity` 상속 | 코드 중복 제거. 수정할 때 한 곳만 고치면 됨 |
| `public Member() {}` | `@NoArgsConstructor(access = PROTECTED)` | 불완전한 객체 생성 방지 |
| `FetchType.EAGER` (기본값) | `FetchType.LAZY` 명시 | N+1 쿼리 문제 예방 (불필요한 DB 조회 방지) |
| point, missionClear 기본값 없음 | `@Builder.Default` + 기본값 0 | Builder로 생성 시에도 기본값이 적용되도록 |

---

## 13. 프로젝트 실행 방법

### 사전 준비

1. **Java 21** 설치
   ```bash
   # Mac (Homebrew)
   brew install openjdk@21

   # 설치 확인
   java -version
   ```

2. **MySQL** 설치 및 데이터베이스 생성
   ```bash
   # Mac (Homebrew)
   brew install mysql
   brew services start mysql

   # MySQL 접속
   mysql -u root -p

   # 데이터베이스 생성
   CREATE DATABASE umc_mission;
   ```

3. **환경 변수** 설정

   프로젝트 루트에 `.env` 파일을 만들거나, IDE에서 환경 변수를 설정합니다:
   ```
   DB_URL=jdbc:mysql://localhost:3306/umc_mission
   DB_USER=root
   DB_PW=your_password
   ```

### 실행

```bash
# 프로젝트 빌드 + 실행
./gradlew bootRun

# 또는 테스트만 실행 (MySQL 없어도 H2로 동작)
./gradlew test
```

### 확인

- 서버 실행 후 브라우저에서 `http://localhost:8080/swagger-ui/index.html` 접속
- Swagger UI에서 API 목록 확인 가능

---

## 파일 설명 요약

| 파일 | 설명 |
|------|------|
| `build.gradle` | 프로젝트 설정 + 라이브러리 목록 |
| `settings.gradle` | 프로젝트 이름 설정 |
| `gradlew` / `gradlew.bat` | Gradle 실행 스크립트 (Mac/Linux용, Windows용) |
| `application.yml` | 앱 설정 (DB 연결 정보, JPA 설정) |
| `application-test.yml` | 테스트 전용 설정 (H2 인메모리 DB) |
| `UmcMissionApplication.java` | 앱 시작점 (`main` 메서드) |
| `BaseEntity.java` | 공통 필드 (createdAt, updatedAt) |
| `JpaAuditingConfig.java` | 자동 시간 기록 활성화 설정 |
| `SwaggerConfig.java` | API 문서 자동 생성 설정 |
| `domain/*/entity/*.java` | JPA Entity (= DB 테이블 정의) |
| `domain/*/enums/*.java` | Enum (= 정해진 값 목록) |
| `domain/*/repository/*.java` | DB 접근 인터페이스 |
| `*Test.java` | 테스트 코드 |
