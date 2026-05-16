package umc.study.umc_mission.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import umc.study.umc_mission.global.apiPayload.code.status.GeneralErrorCode;
import umc.study.umc_mission.global.security.SecurityResponseWriter;

import java.io.IOException;

/**
 * 인증되지 않은 사용자가 보호 자원에 접근했을 때 호출되는 EntryPoint.
 *
 * <p>기본 Spring Security 동작은 로그인 HTML로 리다이렉트하거나 401 + 빈 본문을 내려보낸다.
 * 우리 프로젝트는 모든 API 응답이 {@code ApiResponse} 통일 스키마이므로,
 * 이 핸들러에서 401 + {@code COMMON4010} 응답으로 변환한다.</p>
 *
 * <p>{@code ExceptionTranslationFilter}가 {@code AuthenticationException}을 잡아 여기로 위임.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final SecurityResponseWriter responseWriter;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.warn("[Unauthorized] {} {} — {}", request.getMethod(), request.getRequestURI(),
                authException.getMessage());
        responseWriter.write(response, GeneralErrorCode.UNAUTHORIZED);
    }
}
