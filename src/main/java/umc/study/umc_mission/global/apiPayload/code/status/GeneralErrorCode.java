package umc.study.umc_mission.global.apiPayload.code.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

/**
 * 도메인에 종속되지 않는 공통(글로벌) 에러 코드.
 *
 * <p>특정 도메인에 속하지 않거나, 어떤 도메인에서든 동일하게 발생할 수 있는
 * 일반적인 에러를 정의한다 (예: 잘못된 요청 형식, 인증 실패, 서버 내부 오류).</p>
 *
 * <p>도메인 고유 에러는 각 도메인 패키지의 별도 enum에 두며
 * (예: {@code domain.member.exception.MemberErrorCode}), 모두 {@link BaseErrorCode}
 * 를 구현하므로 {@link umc.study.umc_mission.global.apiPayload.exception.handler.GeneralExceptionAdvice}
 * 가 도메인을 알 필요 없이 동일한 방식으로 응답으로 변환할 수 있다.</p>
 *
 * <p>코드 문자열 컨벤션: {@code COMMON} + HTTP 상태 코드(3자리) + 일련번호.
 * 동일 HTTP 상태가 여러 의미를 가질 수 있으므로 일련번호로 구분한다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {

    // 4xx — 클라이언트 측 문제
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4010", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4030", "접근이 거부되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4040", "요청한 리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON4050", "지원하지 않는 HTTP 메서드입니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "COMMON4150", "지원하지 않는 미디어 타입입니다."),

    // 5xx — 서버 측 문제
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000", "서버 내부 오류가 발생했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
