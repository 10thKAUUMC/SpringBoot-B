package com.example.umc10th.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor

public enum UserErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "사용자를 추가할 수 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
