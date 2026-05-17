package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import org.springframework.http.HttpStatus;

public enum ReviewSuccessCode implements BaseSuccessCode {

    REVIEW_OK(HttpStatus.OK, "REVIEW200", "리뷰 요청에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ReviewSuccessCode(HttpStatus status, String code, String message) {
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
