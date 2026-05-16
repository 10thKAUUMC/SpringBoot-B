package umc.study.umc_mission.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import umc.study.umc_mission.global.apiPayload.code.status.GeneralErrorCode;
import umc.study.umc_mission.global.security.SecurityResponseWriter;

import java.io.IOException;

/**
 * 인증은 되었지만 권한이 부족할 때(403) 호출되는 핸들러.
 *
 * <p>{@code ExceptionTranslationFilter}가 {@code AccessDeniedException}을 잡아 여기로 위임.
 * 응답을 통일된 {@code ApiResponse(COMMON4030)} 형태로 변환한다.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final SecurityResponseWriter responseWriter;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.warn("[Forbidden] {} {} — {}", request.getMethod(), request.getRequestURI(),
                accessDeniedException.getMessage());
        responseWriter.write(response, GeneralErrorCode.FORBIDDEN);
    }
}
