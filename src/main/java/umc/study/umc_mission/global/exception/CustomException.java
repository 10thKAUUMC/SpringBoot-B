package umc.study.umc_mission.global.exception;

import lombok.Getter;

/**
 * 비즈니스 로직에서 발생하는 커스텀 예외.
 *
 * <p>ErrorCode를 포함하여 GlobalExceptionHandler에서
 * 일관된 에러 응답으로 변환된다.</p>
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
