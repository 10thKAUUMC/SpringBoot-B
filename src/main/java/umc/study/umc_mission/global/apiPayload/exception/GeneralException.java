package umc.study.umc_mission.global.apiPayload.exception;

import lombok.Getter;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

/**
 * 프로젝트 전역에서 사용하는 비즈니스 예외의 최상위 타입.
 *
 * <p>{@link RuntimeException}을 상속받는 unchecked 예외다. 트랜잭션
 * 자동 롤백을 위한 일반적인 Spring 관례를 따른다 (체크드 예외는 기본 롤백 안 됨).</p>
 *
 * <p>도메인별 예외(예: {@code MemberException}, {@code StoreException})는
 * 이 클래스를 상속하여 정의한다. {@code @RestControllerAdvice}는 이 부모 타입만
 * 잡으면 모든 도메인 예외를 한 번에 처리할 수 있다 (다형성 활용).</p>
 *
 * <p>예외에 항상 {@link BaseErrorCode}를 동봉하기 때문에, 핸들러는
 * 별도의 분기 없이 {@code errorCode.getStatus()}로 HTTP 상태를,
 * {@code errorCode.getCode()/getMessage()}로 응답 본문을 즉시 만들 수 있다.</p>
 */
@Getter
public class GeneralException extends RuntimeException {

    /**
     * 이 예외가 어떤 종류의 실패인지를 나타내는 에러 코드.
     */
    private final BaseErrorCode errorCode;

    public GeneralException(BaseErrorCode errorCode) {
        // 부모 RuntimeException의 message에도 동일한 메시지를 넣어두면
        // 로그나 디버거에서 `e.getMessage()`만으로도 원인을 파악하기 쉽다.
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
