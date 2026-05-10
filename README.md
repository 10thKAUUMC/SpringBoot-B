# UMC 10기 6주차 미션: API 설계 기초 — JPA (연관관계 + JPQL + 페이징)

> 5주차의 응답 통일 / 전역 에러 핸들러 / 첫 API(마이페이지) 위에,
> **JPA 양방향 연관관계 매핑 + JPQL + Pageable 기반 페이징**을 적용한
> 도메인 API 3개(리뷰 작성 · 내 미션 목록 · 지역 도전 가능 미션)를 추가합니다.

## 6주차 추가 분량 요약

| 영역 | 내용 |
|------|------|
| 양방향 연관관계 | `Member`(reviews/memberMissions), `Store`(missions/reviews), `Region`(stores) 부모측 `@OneToMany` 추가 |
| JPQL + 페이징 | `@Query` + `countQuery` + `Pageable`, `fetch join`으로 N+1 회피 |
| Review API | `POST /api/v1/stores/{storeId}/reviews` |
| Mission API | `GET /api/v1/users/{memberId}/missions?state=&page=&size=` (내 미션, 페이징) |
| Mission API | `GET /api/v1/regions/{regionId}/missions?page=&size=` (지역 도전 가능, 페이징) |
| 응답 페이징 메타 | DTO에 `currentPage/totalPages/totalElements/isLast` 평면화 |

> 자세한 6주차 키워드/설계 메모는 저장소 외부의 [6week.md](../6week.md)를 참고. 5주차 분량(응답 통일 / 에러 핸들러 / 마이페이지 API)은 그대로 유지되며, 아래 5주차 섹션이 그것을 설명합니다.

---

# UMC 10기 5주차 미션: API 응답 통일 + 에러 핸들러 + 마이페이지 API

> 4주차에서 잡아둔 도메인형 아키텍처 위에, **응답 통일 객체 / 전역 에러 핸들러 / 도메인별 ErrorCode·Exception 분리**를 도입하고
> 첫 번째 API(마이페이지 조회)의 **Controller + DTO**를 작성하는 미션입니다.

---

## 5주차 요약 (이번 주차에 한 일)

| 영역 | 내용 |
|------|------|
| 응답 통일 | `ApiResponse<T>` (isSuccess / code / message / result) — `onSuccess`·`onFailure` 정적 팩토리 |
| 에러/성공 코드 | `BaseErrorCode` / `BaseSuccessCode` 인터페이스 + 도메인별 enum 구현 |
| 예외 처리 | `GeneralException` 최상위 + `MemberException` 같은 도메인별 하위 + `@RestControllerAdvice` 1개로 다형성 일괄 처리 |
| Member API | `POST /api/v1/users/me` — 마이페이지 조회 (Controller + Request/Response DTO + Converter + Service) |
| 패키지 신설 | `global/apiPayload/`, `presentation/`, `domain/<name>/exception/`, `domain/<name>/service/` |

---

## 기술 스택

| 기술 | 버전 | 설명 |
|------|------|------|
| Java | 21 (LTS) | 프로그래밍 언어 |
| Spring Boot | 3.4.4 | 웹 프레임워크 |
| Gradle | 8.10.2 | 빌드 도구 |
| Spring Data JPA | - | ORM |
| MySQL | 8.0 | 운영 DB (Docker) |
| H2 | - | 테스트 DB (인메모리) |
| Lombok | - | 보일러플레이트 자동 생성 |
| springdoc-openapi | 2.8.6 | Swagger UI 자동 문서화 |
| Bean Validation | - | 요청 DTO 검증 (`@NotNull` 등) |

---

## 프로젝트 구조

```
src/main/java/umc/study/umc_mission/
├── UmcMissionApplication.java
│
├── domain/                                         ← 도메인 계층 (순수, Spring 의존 최소화)
│   ├── member/
│   │   ├── entity/{Member, MemberRole, MemberLikes}.java
│   │   ├── enums/{Gender, RoleType}.java
│   │   ├── repository/MemberRepository.java        ← 순수 Repository 인터페이스
│   │   ├── service/MemberService.java              ← 5주차 신설: 서비스 인터페이스
│   │   └── exception/                              ← 5주차 신설: 도메인 전용 코드/예외
│   │       ├── MemberErrorCode.java                ← BaseErrorCode 구현
│   │       ├── MemberSuccessCode.java              ← BaseSuccessCode 구현
│   │       └── MemberException.java                ← GeneralException 상속
│   │
│   ├── mission/    {entity, enums, repository}
│   ├── store/      {entity, repository}
│   ├── review/     {entity, repository}
│   ├── alarm/      {entity, enums, repository}
│   ├── region/     {entity, repository}
│   └── point/      {entity, enums, repository}
│
├── infrastructure/                                 ← 인프라 계층 (Spring/JPA 어댑터)
│   ├── member/
│   │   ├── repository/{MemberJpaRepository, MemberRepositoryImpl}.java
│   │   └── service/MemberServiceImpl.java          ← 5주차 신설: 서비스 JPA 구현체
│   └── (다른 도메인은 repository만 — service는 6주차에서 확장)
│
├── presentation/                                   ← 5주차 신설: Web 계층 (Spring Web 의존)
│   └── member/
│       ├── controller/MemberController.java        ← POST /api/v1/users/me
│       ├── dto/
│       │   ├── MemberRequestDTO.java               ← record + Bean Validation
│       │   └── MemberResponseDTO.java              ← record + Builder
│       └── converter/MemberConverter.java          ← Member 엔티티 → 응답 DTO
│
└── global/                                         ← 횡단 관심사
    ├── common/BaseEntity.java                      ← createdAt/updatedAt
    ├── config/{JpaAuditingConfig, SwaggerConfig}.java
    └── apiPayload/                                 ← 5주차 신설: API 응답·예외 통일
        ├── ApiResponse.java                        ← {isSuccess, code, message, result}
        ├── code/
        │   ├── BaseErrorCode.java                  ← 인터페이스 (status/code/message)
        │   ├── BaseSuccessCode.java                ← 인터페이스 (status/code/message)
        │   └── status/
        │       ├── GeneralErrorCode.java           ← 도메인 무관 공통 에러 (4xx/5xx)
        │       └── GeneralSuccessCode.java         ← 도메인 무관 공통 성공 (2xx)
        └── exception/
            ├── GeneralException.java               ← 프로젝트 최상위 비즈니스 예외
            └── handler/
                └── GeneralExceptionAdvice.java     ← @RestControllerAdvice
```

---

## 설계 포인트 — 5주차 추가분

### 1. ApiResponse 응답 통일

모든 API는 동일한 JSON 스키마로 응답합니다.

**성공 예시 (`POST /api/v1/users/me`)**
```json
{
  "isSuccess": true,
  "code": "MEMBER2000",
  "message": "성공적으로 유저를 조회했습니다.",
  "result": {
    "name": "김아리",
    "nickname": "ari_kim",
    "email": "ari@example.com",
    "phoneNumber": "010-1234-5678",
    "point": 2500
  }
}
```

**실패 예시 (회원 부재)**
```json
{
  "isSuccess": false,
  "code": "MEMBER4040",
  "message": "존재하지 않는 회원입니다."
}
```

`result`가 `null`이면 `@JsonInclude(NON_NULL)`로 자동 누락됩니다.

### 2. ErrorCode / SuccessCode 도메인별 분리

**Why** — 단일 enum에 모든 에러를 몰아넣으면 도메인 담당자별 충돌과 가독성 문제가 누적됩니다.
DDD 관점에서 "회원 도메인 에러는 회원이 책임진다"는 경계를 코드로 표현합니다.

```
BaseErrorCode (interface)
 ├── GeneralErrorCode   (글로벌: 400, 401, 404, 500 등)
 ├── MemberErrorCode    (도메인: MEMBER4040 회원 없음, MEMBER4090 이메일 중복 …)
 └── … (다른 도메인은 점진적으로 추가)
```

코드 컨벤션: `<DOMAIN><HTTP_STATUS><SEQ>` (예: `MEMBER4040`, `COMMON5000`)
같은 HTTP 상태 안에서도 의미 분기를 위해 일련번호를 사용합니다.

### 3. 도메인별 Exception + 단일 Advice

```
RuntimeException
 └── GeneralException  ─── 프로젝트 모든 비즈니스 예외의 부모
        └── MemberException  ─── 도메인별 하위 (확장 시 StoreException, …)
```

`GeneralExceptionAdvice`는 `GeneralException` 한 타입만 잡으면 다형성으로 모든 도메인 예외를 처리합니다.

```java
// 도메인 코드
member = memberRepository.findById(id)
    .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

// → 어드바이스가 자동으로 ApiResponse(실패)로 변환
```

ERROR/WARN 로그 레벨도 분리:
- `GeneralException` (예상된 비즈니스 실패) → WARN
- 그 외 일반 `Exception` (예상치 못한 결함) → ERROR + 스택트레이스

### 4. presentation 계층 신설

PR #1에서 **도메인↔인프라**를 분리한 헥사고날 컨벤션과 일관되게,
**Spring Web(컨트롤러/DTO)을 별도 `presentation/`에 격리**합니다.

```
presentation → domain (서비스 인터페이스 호출)
infrastructure → domain (Repository/Service 인터페이스 구현)
domain → 어떤 외부 계층에도 의존 X
```

### 5. record 기반 DTO

모든 DTO는 record로 작성합니다.
- 자동으로 `final` 필드 → 불변성 보장
- 보일러플레이트(생성자/getter/equals) 제거
- 외부 클래스(`MemberRequestDTO`)에 nested static record로 묶어 도메인 단위 네임스페이스 형성

---

## API 명세서

| Method | URI | 설명 | Request | Response Code |
|--------|-----|------|---------|---------------|
| POST | `/api/v1/users/me` | 마이페이지 조회 | `{ "id": 1 }` | `MEMBER2000` 성공 / `MEMBER4040` 회원 없음 |

> **임시 설계 메모** — 본래 마이페이지는 인증된 사용자 대상의 `GET /me`가 자연스럽지만,
> JWT가 9주차에 도입되므로 그 전까지 임시로 회원 ID를 Body로 받는 `POST` 형태로 운영합니다.

자세한 명세는 앱 실행 후 Swagger UI에서 확인하실 수 있습니다.

---

## 실행 방법

### 1. Docker MySQL 시작
```bash
docker-compose up -d
```
`umc_mission` (개발용) + `umc_mission_test` (테스트용) DB가 자동 생성됩니다.

### 2. 환경변수 설정
`.env.example` 복사 → `.env`. Docker 기본값 사용 시 그대로 사용 가능.

### 3. 앱 실행
```bash
./gradlew bootRun
```

### 4. Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

### 5. 테스트
```bash
./gradlew test    # H2 인메모리 — Docker 불필요
```

> ⚠️ **한글 경로 주의** — 프로젝트 경로에 한글이 포함되면 Gradle test worker가 클래스로딩에 실패할 수 있습니다 (Windows 특정 환경). ASCII 경로로 옮기거나 IntelliJ에서 직접 실행하세요.

---

## 셀프 피드백 (4주차 → 5주차)

| Before (4주차) | After (5주차) | Why |
|---|---|---|
| 단일 `ErrorCode` enum (모든 도메인 섞임) | `BaseErrorCode` 인터페이스 + 도메인별 enum 분리 | DDD 도메인 경계 일관성, 담당자 분담 시 충돌 최소화 |
| `CustomException` 단일 클래스 | `GeneralException` + 도메인별 하위 클래스 | 다형성으로 한 어드바이스가 모든 도메인 처리 |
| SuccessCode 없음 | `BaseSuccessCode` + 도메인별 enum | 200 응답에서도 자체 코드로 의미 분기 가능 |
| `ApiResponse {code, message, data}` | `ApiResponse {isSuccess, code, message, result}` | 워크북 표준 형식 + 본문만으로 성공 여부 판별 가능 (게이트웨이 변형 대응) |
| `global/exception/`, `global/response/` 분리 | `global/apiPayload/` 단일 패키지로 통합 | 워크북 컨벤션 + 응답·예외가 한 흐름임을 패키지로 표현 |
| 컨트롤러 자리 없음 | `presentation/` 레이어 신설 | Spring Web을 도메인에서 격리 (헥사고날 일관성) |
| 평범한 클래스 DTO | record 기반 + `@Builder` | 불변성 + 보일러플레이트 제거 |

---

## 핵심 키워드 (워크북 미션)

- **빌더 패턴** — 필드가 많은 객체를 단계적으로 명확하게 조립. record 응답 DTO에서 `@Builder` 활용
- **record vs static class** — record는 불변/간결, static class는 가변/유연. DTO는 record가 적합
- **제네릭** — `ApiResponse<T>`로 어떤 응답 페이로드든 동일한 래퍼 사용
- **@RestControllerAdvice** — 컨트롤러를 가로질러 예외를 가로채는 AOP. 통일 응답으로 변환
- **Optional** — `findById` 결과를 null 대신 안전하게 표현. `.orElseThrow()`로 부재 처리

---

## 다음 주차 예정 (6주차)

- JPA 본격 학습 (관계 매핑, JPQL, 페이징)
- `MemberServiceImpl` 외 다른 도메인 Service 구현체 채우기
- Bean Validation 실패 시 통일 응답 변환 (Advice에 핸들러 추가)
- 리뷰 작성 / 미션 도전 / 가게 목록 등 추가 API
