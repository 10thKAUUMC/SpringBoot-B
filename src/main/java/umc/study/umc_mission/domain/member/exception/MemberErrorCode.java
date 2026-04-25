package umc.study.umc_mission.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

/**
 * 회원(Member) 도메인 전용 에러 코드.
 *
 * <p>Member 도메인 내부에서 발생하는 비즈니스 예외만 정의한다. 다른 도메인에서도 쓸 만한
 * 일반적인 에러는 {@link umc.study.umc_mission.global.apiPayload.code.status.GeneralErrorCode}
 * 에 두고, 이 enum에는 "회원이라는 개념을 다룰 때만 의미가 있는" 에러만 넣는다는 원칙.</p>
 *
 * <p>코드 컨벤션: {@code MEMBER} + HTTP 상태 코드(3자리) + 일련번호.
 * 같은 HTTP 상태(예: 404) 안에서도 "회원 없음"과 "탈퇴한 회원" 등을 구분해야 할 때
 * 일련번호를 늘려가며 추가한다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    /** 식별자에 해당하는 회원이 존재하지 않을 때. */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4040", "존재하지 않는 회원입니다."),

    /** 이미 가입된 이메일로 다시 가입을 시도했을 때. */
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER4090", "이미 사용 중인 이메일입니다."),

    /** 닉네임 형식이나 길이 등이 정책에 어긋날 때. */
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "MEMBER4000", "닉네임 형식이 올바르지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
