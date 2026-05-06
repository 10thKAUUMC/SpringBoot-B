package com.example.umc10th.global.apiPayload;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.apiPayload.code.status.GeneralSuccessCode;

public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private final T result;

    private ApiResponse(Boolean isSuccess, String code, String message, T result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> ApiResponse<T> onSuccess(T result) {
        return onSuccess(GeneralSuccessCode.OK, result);
    }

    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode successCode, T result) {
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T result) {
        return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), result);
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}
