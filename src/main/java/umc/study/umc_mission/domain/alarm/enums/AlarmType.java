package umc.study.umc_mission.domain.alarm.enums;

/**
 * 알림(Alarm)의 유형을 나타내는 열거형(Enum).
 *
 * <p>사용자에게 전송되는 알림의 종류를 구분한다.
 * Enum으로 관리하면 알림을 종류별로 필터링하거나,
 * 특정 종류의 알림만 on/off 할 수 있는 설정 기능을 구현하기 편리하다.</p>
 *
 * <ul>
 *   <li>{@link #MISSION} — 미션 관련 알림 (새 미션 등록, 미션 완료 등)</li>
 *   <li>{@link #REVIEW} — 리뷰 관련 알림 (내 가게에 새 리뷰 등)</li>
 *   <li>{@link #POINT} — 포인트 관련 알림 (포인트 적립, 사용 등)</li>
 *   <li>{@link #SYSTEM} — 시스템 알림 (공지사항, 점검 안내 등)</li>
 * </ul>
 */
public enum AlarmType {

    /** 미션 관련 알림 — 새 미션 등록, 미션 완료 알림 등 */
    MISSION,

    /** 리뷰 관련 알림 — 내 가게에 새 리뷰가 작성되었을 때 등 */
    REVIEW,

    /** 포인트 관련 알림 — 포인트 적립/사용 시 */
    POINT,

    /** 시스템 알림 — 서비스 공지사항, 점검 안내 등 */
    SYSTEM
}
