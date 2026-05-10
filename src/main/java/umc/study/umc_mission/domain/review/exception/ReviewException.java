package umc.study.umc_mission.domain.review.exception;

import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;
import umc.study.umc_mission.global.apiPayload.exception.GeneralException;

/**
 * 리뷰(Review) 도메인 비즈니스 예외.
 *
 * <p>{@link GeneralException}을 상속하므로 {@code GeneralExceptionAdvice}가 다형성으로 처리한다.
 * 도메인 별로 클래스를 분리해 두면 추후 도메인별 catch 분기가 가능하다.</p>
 */
public class ReviewException extends GeneralException {

    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
