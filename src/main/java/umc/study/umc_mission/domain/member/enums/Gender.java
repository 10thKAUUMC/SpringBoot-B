package umc.study.umc_mission.domain.member.enums;

/**
 * 회원의 성별을 나타내는 열거형(Enum).
 *
 * <p>열거형이란? — 미리 정해진 상수 값들만 가질 수 있는 특수한 타입이다.
 * 문자열(String)로 "MALE", "FEMALE"을 직접 쓰면 오타("MALEE" 등)가 발생해도
 * 컴파일 시점에 잡아내지 못하지만, Enum을 사용하면 컴파일러가 잘못된 값을 즉시 감지한다.</p>
 *
 * <p>엔티티(Entity)에서 이 Enum을 필드로 선언할 때
 * {@code @Enumerated(EnumType.STRING)}을 함께 사용하면,
 * 데이터베이스에는 "MALE", "FEMALE", "OTHER" 같은 문자열 그대로 저장된다.</p>
 *
 * <ul>
 *   <li>{@link #MALE} — 남성</li>
 *   <li>{@link #FEMALE} — 여성</li>
 *   <li>{@link #OTHER} — 기타 (선택하지 않음 또는 그 외)</li>
 * </ul>
 */
public enum Gender {

    /** 남성 */
    MALE,

    /** 여성 */
    FEMALE,

    /** 기타 — 성별을 밝히지 않거나 위 두 가지에 해당하지 않는 경우 */
    OTHER
}
