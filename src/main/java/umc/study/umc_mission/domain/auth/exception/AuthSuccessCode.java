package umc.study.umc_mission.domain.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseSuccessCode;

/**
 * 인증/회원가입(Auth) 도메인 전용 성공 코드.
 */
@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements BaseSuccessCode {

    /** 회원가입 성공 (자원 생성). */
    SIGN_UP(HttpStatus.CREATED, "AUTH2010", "성공적으로 회원가입했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
