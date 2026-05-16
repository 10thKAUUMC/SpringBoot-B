package umc.study.umc_mission.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.study.umc_mission.global.apiPayload.ApiResponse;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

import java.io.IOException;

/**
 * 시큐리티 단(어드바이스 밖)에서 발생하는 인증·인가 실패를
 * 일반 API와 동일한 {@code ApiResponse} JSON으로 응답하기 위한 공통 라이터.
 *
 * <p>{@link umc.study.umc_mission.global.security.handler.CustomAuthenticationEntryPoint},
 * {@link umc.study.umc_mission.global.security.handler.CustomAccessDeniedHandler}가
 * 동일한 직렬화/Content-Type 설정을 반복하지 않도록 한 곳으로 모았다.</p>
 *
 * <p>Spring이 관리하는 ObjectMapper를 주입받아 사용 — 새로 인스턴스를 만들면
 * 글로벌 직렬화 설정(LocalDateTime 모듈 등)이 적용되지 않을 수 있기 때문.</p>
 */
@Component
@RequiredArgsConstructor
public class SecurityResponseWriter {

    private final ObjectMapper objectMapper;

    /** 주어진 ErrorCode로 통일된 ApiResponse JSON을 응답에 씀. */
    public void write(HttpServletResponse response, BaseErrorCode code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());
        ApiResponse<Void> body = ApiResponse.onFailure(code, null);
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
