package umc.study.umc_mission.global.apiPayload.code;

import org.springframework.http.HttpStatus;

/**
 * 모든 에러 코드 enum이 구현해야 하는 공통 인터페이스.
 *
 * <p>도메인형 아키텍처에서는 각 도메인이 자신의 에러 코드 enum을 따로 둔다
 * (예: MemberErrorCode, StoreErrorCode). 이 인터페이스는 그 enum들이 공통으로
 * 노출해야 하는 메서드를 강제하여, GlobalExceptionHandler 같은 횡단 코드가
 * 도메인을 몰라도 에러 정보를 동일하게 꺼낼 수 있게 한다.</p>
 *
 * <p>구현하는 enum은 다음 세 가지를 반드시 제공한다.</p>
 * <ul>
 *   <li>{@link #getStatus()} — 응답으로 내보낼 HTTP 상태 코드 (4xx / 5xx)</li>
 *   <li>{@link #getCode()} — 도메인 식별자가 포함된 자체 에러 코드 문자열 (예: {@code "MEMBER4001"})</li>
 *   <li>{@link #getMessage()} — 클라이언트에게 그대로 노출 가능한 사용자용 메시지</li>
 * </ul>
 *
 * <p>HTTP 상태와 별개로 자체 코드 문자열을 두는 이유는,
 * 동일 HTTP 상태(예: 404) 안에서도 "회원 없음"과 "가게 없음"을 프론트가
 * 구분해서 처리할 수 있어야 하기 때문이다.</p>
 */
public interface BaseErrorCode {

    /**
     * 응답에 사용할 HTTP 상태 코드.
     *
     * <p>일반적으로 4xx(클라이언트 잘못) 또는 5xx(서버 잘못)을 반환한다.</p>
     */
    HttpStatus getStatus();

    /**
     * 자체 에러 코드 문자열.
     *
     * <p>도메인 prefix + HTTP 상태 + 일련번호 형태를 권장한다 (예: {@code "MEMBER4041"}).
     * 프론트엔드가 분기 처리할 수 있도록 도메인별로 유일해야 한다.</p>
     */
    String getCode();

    /**
     * 클라이언트에게 노출할 사용자용 메시지.
     *
     * <p>스택트레이스나 내부 구현 정보가 새지 않도록 추상화된 표현을 사용한다.</p>
     */
    String getMessage();
}
