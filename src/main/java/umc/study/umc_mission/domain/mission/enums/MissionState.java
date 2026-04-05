package umc.study.umc_mission.domain.mission.enums;

/**
 * 미션의 진행 상태를 나타내는 열거형(Enum).
 *
 * <p>회원이 특정 미션에 도전하면 처음에는 {@link #CHALLENGING} 상태가 되고,
 * 미션 조건을 달성하면 {@link #COMPLETED}로 변경된다.
 * 이처럼 상태(State)를 Enum으로 관리하면 코드 어디서든 일관된 값만 사용할 수 있고,
 * if/switch 문에서 상태를 명확하게 분기할 수 있다.</p>
 *
 * <ul>
 *   <li>{@link #CHALLENGING} — 도전 중 (아직 미션을 완료하지 않은 상태)</li>
 *   <li>{@link #COMPLETED} — 완료됨 (미션 조건을 달성한 상태)</li>
 * </ul>
 */
public enum MissionState {

    /** 도전 중 — 회원이 미션에 참여했지만 아직 완료하지 못한 상태 */
    CHALLENGING,

    /** 완료 — 미션 조건을 모두 달성하여 보상을 받을 수 있는 상태 */
    COMPLETED
}
