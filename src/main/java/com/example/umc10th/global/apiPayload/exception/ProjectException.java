package com.example.umc10th.global.apiPayload.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;

public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public ProjectException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }
}
