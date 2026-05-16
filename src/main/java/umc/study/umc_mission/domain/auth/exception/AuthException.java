package umc.study.umc_mission.domain.auth.exception;

import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;
import umc.study.umc_mission.global.apiPayload.exception.GeneralException;

/**
 * 인증/회원가입(Auth) 도메인 비즈니스 예외.
 *
 * <p>{@link GeneralException}을 상속하므로 전역 어드바이스가 다형성으로 처리한다.</p>
 */
public class AuthException extends GeneralException {

    public AuthException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
