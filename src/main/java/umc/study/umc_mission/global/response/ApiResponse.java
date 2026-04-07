package umc.study.umc_mission.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import umc.study.umc_mission.global.exception.ErrorCode;

/**
 * 모든 API 응답에 공통으로 사용하는 래퍼 클래스.
 *
 * <p>성공 시 {@link #success(Object)}를, 실패 시 {@link #error(ErrorCode)}를 사용한다.
 * data 필드는 null이면 JSON에서 제외된다.</p>
 *
 * <p>응답 예시 (성공):</p>
 * <pre>
 * {
 *   "code": "SUCCESS",
 *   "message": "요청이 성공적으로 처리되었습니다.",
 *   "data": { ... }
 * }
 * </pre>
 *
 * <p>응답 예시 (실패):</p>
 * <pre>
 * {
 *   "code": "MEMBER_404",
 *   "message": "존재하지 않는 회원입니다."
 * }
 * </pre>
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code("SUCCESS")
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();
    }

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return ApiResponse.<Void>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
