package umc.study.umc_mission.global.apiPayload.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.study.umc_mission.global.apiPayload.ApiResponse;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;
import umc.study.umc_mission.global.apiPayload.code.status.GeneralErrorCode;
import umc.study.umc_mission.global.apiPayload.exception.GeneralException;

/**
 * 전역 예외 처리 어드바이스.
 *
 * <p>컨트롤러 → 서비스 → 리포지토리 어디에서 던져진 예외든, 이 클래스로 모이게 된다
 * ({@code @RestControllerAdvice}는 모든 컨트롤러에 가로 방향으로 끼어든다).
 * 잡힌 예외는 모두 {@link ApiResponse} 형태로 변환되어 클라이언트에게는 항상 동일한 JSON
 * 스키마로 응답된다.</p>
 *
 * <p>핸들러 매칭 우선순위: 더 구체적인 타입(자식)이 더 일반적인 타입(부모)보다 우선이다.
 * 따라서 {@link GeneralException}이 먼저 매칭되고, 그래도 잡히지 않은 예상치 못한 예외만
 * 마지막 {@link Exception} 핸들러로 떨어진다.</p>
 */
@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

    /**
     * 비즈니스 로직에서 의도적으로 던진 예외를 처리한다.
     *
     * <p>{@link GeneralException}을 상속한 모든 도메인 예외(MemberException 등)가 여기로 모인다.
     * 이미 {@link BaseErrorCode}를 동봉하고 있으므로 그대로 응답으로 변환하면 된다.</p>
     *
     * <p>로그 레벨이 WARN인 이유: 이 예외는 "예상된 실패"이지 시스템 결함이 아니다.
     * 알림이 가야 하는 진짜 장애는 아래 {@link #handleException(Exception)}에서 ERROR로 남긴다.</p>
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        log.warn("[GeneralException] {} {}", errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    /**
     * 위에서 잡지 못한 모든 예외(NPE, IllegalState 등)를 마지막에 받아내는 안전망.
     *
     * <p>여기로 들어왔다는 건 우리가 처리를 빠뜨렸다는 뜻이므로 ERROR로 로깅하고
     * 스택트레이스를 남겨 추적이 가능하도록 한다.</p>
     *
     * <p>응답에는 내부 메시지를 그대로 노출하지 않고 {@link GeneralErrorCode#INTERNAL_SERVER_ERROR}
     * 의 일반화된 메시지를 쓴다. 다만 디버깅에 도움이 되도록 {@code result} 필드에
     * 예외 메시지를 담아 보낸다 (운영 환경에서 민감 정보가 새지 않도록 향후 프로파일별로
     * 필터링하는 것을 권장).</p>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        log.error("[Unhandled Exception]", e);
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, e.getMessage()));
    }
}
