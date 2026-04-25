package umc.study.umc_mission.global.apiPayload.code;

import org.springframework.http.HttpStatus;

/**
 * 모든 성공 코드 enum이 구현해야 하는 공통 인터페이스.
 *
 * <p>{@link BaseErrorCode}와 짝을 이루는 성공용 인터페이스. 컨트롤러가
 * {@code ApiResponse.onSuccess(code, result)} 형태로 응답을 만들 때,
 * 도메인별 SuccessCode enum (예: MemberSuccessCode)을 받아서 응답 본문의
 * {@code code}, {@code message} 필드를 일관된 방식으로 채울 수 있게 해준다.</p>
 *
 * <p>HTTP 200 한 가지 안에서도 "유저 조회 성공"과 "유저 생성 성공"의 자체 코드를
 * 구분해두면, 프론트가 응답 분기와 i18n 메시지 처리를 더 명확히 할 수 있다.</p>
 */
public interface BaseSuccessCode {

    /**
     * 응답에 사용할 HTTP 상태 코드. 보통 2xx 범위.
     */
    HttpStatus getStatus();

    /**
     * 자체 성공 코드 문자열. 도메인 prefix + HTTP 상태 + 일련번호를 권장한다.
     * 예: {@code "MEMBER2001"} (회원 조회 성공)
     */
    String getCode();

    /**
     * 클라이언트에게 노출할 사용자용 성공 메시지.
     */
    String getMessage();
}
