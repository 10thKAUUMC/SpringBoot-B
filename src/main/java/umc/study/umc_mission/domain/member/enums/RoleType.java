package umc.study.umc_mission.domain.member.enums;

/**
 * 회원의 역할(권한) 유형을 나타내는 열거형(Enum).
 *
 * <p>역할(Role)을 별도 테이블로 관리하면 유연하지만 구조가 복잡해진다.
 * 역할 종류가 고정적이고 적은 경우에는 Enum으로 대체하면 코드가 훨씬 단순해진다.
 * 이 프로젝트에서는 역할이 3가지(USER, ADMIN, MANAGER)로 한정되므로 Enum을 사용한다.</p>
 *
 * <p>사용 예시: 관리자 전용 API에 접근할 때 현재 로그인한 회원의 RoleType이
 * {@link #ADMIN}인지 확인하여 접근을 허용하거나 거부할 수 있다.</p>
 *
 * <ul>
 *   <li>{@link #USER} — 일반 사용자. 가장 기본적인 권한을 가진다.</li>
 *   <li>{@link #ADMIN} — 관리자. 시스템 전체를 관리할 수 있는 최고 권한.</li>
 *   <li>{@link #MANAGER} — 매니저. 일반 사용자보다 높지만 관리자보다 제한된 권한.</li>
 * </ul>
 */
public enum RoleType {

    /** 일반 사용자 — 기본 권한 */
    USER,

    /** 관리자 — 시스템 전체 관리 권한 */
    ADMIN,

    /** 매니저 — 중간 관리 권한 (예: 가게 관리자) */
    MANAGER
}
