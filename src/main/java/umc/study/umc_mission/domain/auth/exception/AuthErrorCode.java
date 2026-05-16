package umc.study.umc_mission.domain.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

/**
 * 인증/회원가입(Auth) 도메인 전용 에러 코드.
 *
 * <p>다른 도메인 컨벤션과 동일: {@code AUTH + HTTP상태(3) + 일련번호}.</p>
 */
@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    /** 회원가입 시 이미 사용 중인 이메일. */
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "AUTH4090", "이미 사용 중인 이메일입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
