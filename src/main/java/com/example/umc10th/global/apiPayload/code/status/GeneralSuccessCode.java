package com.example.umc10th.global.apiPayload.code.status;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import org.springframework.http.HttpStatus;

public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK, "COMMON200", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "리소스가 생성되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    GeneralSuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
