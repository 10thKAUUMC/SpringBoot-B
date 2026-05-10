package umc.study.umc_mission.domain.mission.exception;

import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;
import umc.study.umc_mission.global.apiPayload.exception.GeneralException;

/**
 * 미션(Mission) 도메인 비즈니스 예외.
 *
 * <p>{@link GeneralException}을 상속하므로 전역 어드바이스가 다형성으로 처리한다.</p>
 */
public class MissionException extends GeneralException {

    public MissionException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
