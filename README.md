# UMC 10기 4주차 미션: ERD 기반 도메인형 아키텍처 Spring 프로젝트 세팅

> 0주차에 작성한 ERD를 바탕으로, Spring Boot 프로젝트를 세팅하고 JPA Entity를 구현하는 미션입니다.

---

## 기술 스택

| 기술 | 버전 | 설명 |
|------|------|------|
| Java | 21 (LTS) | 프로그래밍 언어 |
| Spring Boot | 3.4.4 | 웹 프레임워크 |
| Gradle | 8.10.2 | 빌드 도구 |
| Spring Data JPA | - | DB를 Java 코드로 다루는 기술 (ORM) |
| MySQL | 8.0 | 데이터베이스 (Docker) |
| Lombok | - | 반복 코드(getter, 생성자 등) 자동 생성 |
| Swagger (springdoc) | 2.8.6 | API 문서 자동 생성 |

---

## 프로젝트 구조

```
src/main/java/umc/study/umc_mission/
├── UmcMissionApplication.java              ← 앱 시작점
│
├── domain/                                  ← 도메인별 코드
│   ├── member/                              ← 회원 도메인
│   │   ├── entity/
│   │   │   ├── Member.java                  ← 회원 테이블
│   │   │   ├── MemberRole.java              ← 회원-역할 매핑 (RoleType Enum 사용)
│   │   │   └── MemberLikes.java             ← 회원-음식취향 매핑
│   │   ├── enums/
│   │   │   ├── Gender.java                  ← 성별 (MALE, FEMALE, OTHER)
│   │   │   └── RoleType.java                ← 역할 (USER, ADMIN, MANAGER)
│   │   └── repository/
│   │       ├── MemberRepository.java        ← 순수 인터페이스 (도메인)
│   │       ├── MemberJpaRepository.java     ← Spring Data JPA (인프라)
│   │       └── impl/MemberRepositoryImpl.java ← 구현체
│   │
│   ├── mission/                             ← 미션 도메인
│   │   ├── entity/{Mission, MemberMission}.java
│   │   ├── enums/{MissionState, MissionType}.java
│   │   └── repository/ (같은 패턴)
│   │
│   ├── store/                               ← 가게 도메인
│   │   ├── entity/{Store, FoodCategory}.java
│   │   └── repository/
│   │
│   ├── review/                              ← 리뷰 도메인
│   │   ├── entity/Review.java
│   │   └── repository/
│   │
│   ├── alarm/                               ← 알림 도메인
│   │   ├── entity/Alarm.java
│   │   ├── enums/AlarmType.java
│   │   └── repository/
│   │
│   ├── region/                              ← 지역 도메인 (독립 분리)
│   │   ├── entity/Region.java
│   │   └── repository/
│   │
│   └── point/                               ← 포인트 도메인 (독립 분리)
│       ├── entity/PointHistory.java
│       ├── enums/PointType.java
│       └── repository/
│
└── global/                                  ← 공통 코드
    ├── common/BaseEntity.java               ← createdAt/updatedAt 공통 관리
    └── config/
        ├── JpaAuditingConfig.java           ← JPA Auditing 활성화
        └── SwaggerConfig.java               ← Swagger UI 설정
```

---

## 설계 포인트

### 도메인형 아키텍처
계층형(entity/, repository/, service/ 각각 한 폴더)이 아닌, **기능(도메인) 단위**로 코드를 분류합니다.
"회원 관련 코드 수정" → `member/` 폴더만 보면 됩니다.

### Repository / RepositoryImpl 분리
```
MemberRepository (순수 interface)  ←  Service가 의존
    ↑ implements
MemberRepositoryImpl (@Repository)  →  MemberJpaRepository (Spring Data JPA)
```
- **MemberRepository**: 비즈니스 메서드만 정의. JPA 의존 없음
- **MemberJpaRepository**: Spring Data JPA interface. Impl 내부에서만 사용
- **MemberRepositoryImpl**: 실제 구현. JpaRepository에 위임

이 패턴으로 도메인 레이어가 인프라(JPA)에 의존하지 않습니다.

### Enum 활용
- `Gender` (MALE, FEMALE, OTHER)
- `RoleType` (USER, ADMIN, MANAGER) — 기존 Role 엔티티를 Enum으로 대체
- `MissionState` (CHALLENGING, COMPLETED)
- `MissionType` (VISIT, REVIEW, PURCHASE, EVENT)
- `PointType` (EARN, USE)
- `AlarmType` (MISSION, REVIEW, POINT, SYSTEM)

모두 `@Enumerated(EnumType.STRING)`으로 문자열 저장.

### @Getter 미사용 — 수동 Getter
Lombok `@Getter` 대신 필요한 getter만 직접 작성.
컬렉션 필드는 `Collections.unmodifiableList()`로 읽기 전용 반환.

### Java Beans 네이밍 관례
- `private Boolean opened` (필드에 `is` 접두사 X)
- `public Boolean isOpened()` (getter에 `is` 접두사 O)

---

## ERD — 엔티티 관계도

```
Region (1) ──< Store (N) ──< Mission (N)
                │                │
                │          MemberMission >── Member
                │                            │  │
             Review >── Member               │  │
                                      MemberRole  MemberLikes >── FoodCategory
                                             
Alarm >── Member          PointHistory >── Member, Mission
```

| 도메인 | Entity | 설명 |
|--------|--------|------|
| member | Member, MemberRole, MemberLikes | 회원, 역할(Enum), 음식 취향 |
| mission | Mission, MemberMission | 미션, 회원-미션 참여 (state 보유) |
| store | Store, FoodCategory | 가게, 음식 카테고리 |
| review | Review | 가게 리뷰 (별점 + 내용) |
| alarm | Alarm | 회원 알림 (확인 여부 추적) |
| region | Region | 지역 (독립 도메인) |
| point | PointHistory | 포인트 적립/사용 이력 (INSERT-only) |

---

## 실행 방법

### 1. Docker MySQL 시작
```bash
docker-compose up -d
```
`umc_mission` (개발용) + `umc_mission_test` (테스트용) DB가 자동 생성됩니다.

### 2. 앱 실행
```bash
./gradlew bootRun
```

### 3. Swagger UI 확인
```
http://localhost:8080/swagger-ui/index.html
```

### 4. 테스트 실행
```bash
# Docker MySQL이 떠있어야 합니다
./gradlew test
```

### 환경 변수 (선택)
`.env.example` 참고. Docker 기본값 사용 시 별도 설정 불필요.

---

## 셀프 피드백 (0주차 ERD → 4주차 구현)

| Before | After | Why |
|--------|-------|-----|
| VARCHAR로 성별/미션종류 저장 | Enum 전환 | 타입 안전성 + 잘못된 값 방지 |
| Role 별도 테이블 | RoleType Enum | 값이 고정적이므로 단순화 |
| `is_opened` TIMESTAMP | `Boolean opened` | 의미에 맞는 타입 + Java Beans 관례 |
| `open_time` DATETIME | `LocalTime` | 시간만 필요 (날짜 불필요) |
| createdAt/updatedAt 중복 | BaseEntity 상속 | DRY 원칙 |
| `@NoArgsConstructor` public | PROTECTED | 무분별한 객체 생성 방지 |
| `@AllArgsConstructor` public | PRIVATE | Builder 전용, 외부 노출 차단 |
| `@Getter` 클래스 레벨 | 수동 getter | 컬렉션 방어적 복사, 세밀한 제어 |
| FetchType 미지정 (EAGER) | LAZY 명시 | N+1 문제 사전 방지 |
| FoodKind | FoodCategory | 네이밍 명확화 |
| Region이 store/ 하위 | region/ 독립 도메인 | 도메인 경계 분리 |
| PointHistory가 mission/ 하위 | point/ 독립 도메인 | 도메인 경계 분리 |
| Repository = JpaRepository | Repository + Impl 분리 | 도메인-인프라 의존성 분리 |
| H2 인메모리 테스트 | Docker MySQL 테스트 | 실제 DB와 동일 환경 |

---

## 테스트

| 테스트 | 대상 | 검증 내용 |
|--------|------|----------|
| `MemberRepositoryTest` | 회원 저장/조회 | ID 자동 생성, Builder.Default 기본값 |
| `MissionRepositoryTest` | 미션 저장 + 연관관계 | Store → Region 연관관계 정상 동작 |
| `ReviewRepositoryTest` | 리뷰 저장 + 다중 연관관계 | Member + Store 양방향 접근 |
| `UmcMissionApplicationTests` | 앱 컨텍스트 로드 | Spring 정상 기동 확인 |

```bash
./gradlew test
# 5 tests, 0 failures
```
