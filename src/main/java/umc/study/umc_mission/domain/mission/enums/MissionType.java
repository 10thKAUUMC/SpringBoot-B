package umc.study.umc_mission.domain.mission.enums;

/**
 * 미션의 종류를 나타내는 열거형(Enum).
 *
 * <p>가게(Store)가 다양한 유형의 미션을 등록할 수 있는데,
 * 미션의 유형을 문자열이 아닌 Enum으로 관리하면
 * 허용되지 않는 유형이 실수로 들어가는 것을 방지할 수 있다.</p>
 *
 * <ul>
 *   <li>{@link #VISIT} — 방문 미션. 가게를 직접 방문하면 완료.</li>
 *   <li>{@link #REVIEW} — 리뷰 미션. 리뷰를 작성하면 완료.</li>
 *   <li>{@link #PURCHASE} — 구매 미션. 일정 금액 이상 구매하면 완료.</li>
 *   <li>{@link #EVENT} — 이벤트 미션. 특별 이벤트 기간에만 참여 가능한 미션.</li>
 * </ul>
 */
public enum MissionType {

    /** 방문 미션 — 가게에 직접 방문 */
    VISIT,

    /** 리뷰 미션 — 가게에 리뷰 작성 */
    REVIEW,

    /** 구매 미션 — 일정 금액 이상 구매 */
    PURCHASE,

    /** 이벤트 미션 — 특별 이벤트 참여 */
    EVENT
}
