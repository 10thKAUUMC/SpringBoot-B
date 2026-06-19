package com.example.umc10th.domain.user.exception;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum UserSuccessCode implements BaseSuccessCode {
    SIGNUP_SUCCESS(HttpStatus.OK,"USER_001","회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,"USER_002","로그인에 성공하였습니다.");

    private final HttpStatus Status;
    private final String code;
    private final String message;
}
