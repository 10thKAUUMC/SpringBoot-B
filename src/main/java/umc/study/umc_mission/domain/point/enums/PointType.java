package umc.study.umc_mission.domain.point.enums;

/**
 * 포인트 내역의 유형을 나타내는 열거형(Enum).
 *
 * <p>포인트 시스템에서는 "적립"과 "사용" 두 가지 동작이 존재한다.
 * 이를 Enum으로 구분하면, 포인트 내역(PointHistory)을 조회할 때
 * 적립 내역만 또는 사용 내역만 필터링하기 쉽고,
 * 잘못된 값(예: "EARRN")이 들어가는 실수를 방지할 수 있다.</p>
 *
 * <ul>
 *   <li>{@link #EARN} — 포인트 적립 (미션 완료 보상 등)</li>
 *   <li>{@link #USE} — 포인트 사용 (상품 교환, 할인 적용 등)</li>
 * </ul>
 */
public enum PointType {

    /** 적립 — 미션 완료, 리뷰 작성 등으로 포인트를 획득 */
    EARN,

    /** 사용 — 보유 포인트를 소비 (상품 교환, 할인 등) */
    USE
}
