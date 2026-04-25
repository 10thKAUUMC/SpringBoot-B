package umc.study.umc_mission.domain.member.exception;

import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;
import umc.study.umc_mission.global.apiPayload.exception.GeneralException;

/**
 * 회원(Member) 도메인에서 발생하는 비즈니스 예외.
 *
 * <p>{@link GeneralException}을 상속하므로 {@code GeneralExceptionAdvice}가 자동으로
 * 잡아 통일된 응답으로 변환한다. 도메인 측에서는 호출 시점에 어떤 ErrorCode인지만
 * 명확히 지정하면 된다.</p>
 *
 * <p>사용 예:</p>
 * <pre>{@code
 * memberRepository.findById(id)
 *         .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
 * }</pre>
 *
 * <p>도메인별로 별도 예외 클래스를 두는 이유는, 호출자가 {@code catch} 블록에서
 * 도메인 단위로 분기 처리할 여지를 남기기 위함이다 (예: 보상 트랜잭션, 비동기 재시도 정책).
 * 단순 응답 변환만 필요하다면 부모 타입만으로도 충분하지만, 향후 확장성을 위해 분리해 둔다.</p>
 */
public class MemberException extends GeneralException {

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
