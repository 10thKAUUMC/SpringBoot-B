package umc.study.umc_mission.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;
import umc.study.umc_mission.global.apiPayload.code.BaseSuccessCode;
import umc.study.umc_mission.global.apiPayload.code.status.GeneralSuccessCode;

/**
 * 모든 API 응답에 공통으로 사용하는 응답 래퍼.
 *
 * <p>프론트엔드와 약속한 통일된 JSON 형식을 보장한다. 컨트롤러는
 * {@link #onSuccess(Object)} / {@link #onSuccess(BaseSuccessCode, Object)} /
 * {@link #onFailure(BaseErrorCode, Object)} 정적 팩토리 메서드를 통해서만 인스턴스를 만들고,
 * 직접 생성자를 호출하지 않는다 (그래서 생성자는 PRIVATE).</p>
 *
 * <p>응답 예시 (성공):</p>
 * <pre>{@code
 * {
 *   "isSuccess": true,
 *   "code": "MEMBER2000",
 *   "message": "성공적으로 유저를 조회했습니다.",
 *   "result": { "name": "...", "email": "..." }
 * }
 * }</pre>
 *
 * <p>응답 예시 (실패):</p>
 * <pre>{@code
 * {
 *   "isSuccess": false,
 *   "code": "MEMBER4040",
 *   "message": "존재하지 않는 회원입니다."
 * }
 * }</pre>
 *
 * <p>{@code result}가 null인 경우 {@link JsonInclude.Include#NON_NULL} 옵션으로
 * 응답 JSON에서 자동으로 빠진다 (위 실패 예시 참조).</p>
 *
 * @param <T> {@code result} 필드에 들어갈 페이로드 타입. 실패 응답은 보통 {@code Void}
 *            를 사용하지만, 예외 메시지 같은 추가 정보를 담을 때는 {@code String} 등을 쓸 수 있다.
 */
@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    /**
     * 성공/실패 여부. 프론트가 분기할 때 가장 먼저 보는 필드.
     *
     * <p>HTTP 상태 코드와 별개로 명시하는 이유: 일부 게이트웨이/네트워크 환경에서
     * 4xx/5xx 응답이 변형/소실되는 경우가 있고, 응답 본문 자체로도 결과를
     * 판단할 수 있어야 견고하기 때문이다.</p>
     */
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    /**
     * 자체 정의 코드 문자열. {@code MEMBER2000}처럼 도메인 prefix를 포함한다.
     */
    private final String code;

    /**
     * 사용자에게 노출 가능한 메시지.
     */
    private final String message;

    /**
     * 응답 페이로드. 성공 시 DTO, 실패 시 보통 null.
     * null이면 응답 JSON에서 제외된다.
     */
    private final T result;

    // ────────────────────────── 정적 팩토리 메서드 ──────────────────────────

    /**
     * 성공 응답 — 기본 코드({@link GeneralSuccessCode#OK}) 사용.
     *
     * <p>도메인 고유 SuccessCode를 굳이 만들 필요가 없는 단순 응답에 사용한다.</p>
     *
     * @param result 응답 본문에 실을 페이로드 (null 허용)
     */
    public static <T> ApiResponse<T> onSuccess(T result) {
        return onSuccess(GeneralSuccessCode.OK, result);
    }

    /**
     * 성공 응답 — 도메인/기능별 SuccessCode 명시.
     *
     * <p>예: {@code ApiResponse.onSuccess(MemberSuccessCode.GET_MY_PAGE, dto)}.</p>
     *
     * @param code   {@link BaseSuccessCode} 구현체 (예: MemberSuccessCode.XXX)
     * @param result 응답 본문 페이로드
     */
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    /**
     * 실패 응답.
     *
     * <p>{@link umc.study.umc_mission.global.apiPayload.exception.handler.GeneralExceptionAdvice}
     * 에서 잡힌 예외를 응답으로 변환할 때 호출된다. 예외 메시지나 검증 오류 상세를
     * {@code result}에 담아 보낼 수도 있다.</p>
     *
     * @param code   {@link BaseErrorCode} 구현체
     * @param result 추가 디버깅 정보 (보통 null)
     */
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }
}
