package umc.study.umc_mission.domain.member.enums;

/**
 * 회원 역할 유형.
 * 기존 Role 엔티티(별도 테이블)를 Enum으로 대체하여 단순화.
 */
public enum RoleType {
    USER,
    ADMIN,
    MANAGER
}
