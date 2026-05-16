package umc.study.umc_mission.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import umc.study.umc_mission.global.security.handler.CustomAccessDeniedHandler;
import umc.study.umc_mission.global.security.handler.CustomAuthenticationEntryPoint;

/**
 * Spring Security 설정 (8주차).
 *
 * <p>핵심 정책:</p>
 * <ul>
 *   <li><b>Public API</b>: 회원가입(POST /api/v1/auth/sign-up) + Swagger UI + 로그인 페이지 — 비로그인 허용</li>
 *   <li><b>Private API</b>: 그 외 전부 — 인증 필요</li>
 *   <li>폼 로그인 — 워크북 가이드대로 로그인 성공 시 Swagger UI로 리다이렉트</li>
 *   <li>인증/인가 실패는 {@code CustomAuthenticationEntryPoint} / {@code CustomAccessDeniedHandler}가
 *       잡아 {@code ApiResponse} 통일 응답으로 변환</li>
 *   <li>CSRF: REST API + Stateless 운영을 가정해 비활성화 (폼 로그인은 같은 도메인에서만 사용)</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Public(비로그인 허용) URI 목록.
     *
     * <p>Swagger 리소스는 개발 편의를 위해 항상 열어둔다. 운영 환경에서는 프로파일 분기 권장.</p>
     */
    private static final String[] PUBLIC_URIS = {
            // Swagger UI / OpenAPI 문서
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            // 인증 관련 (회원가입 + 폼 로그인)
            "/api/v1/auth/**",
            "/login",
            "/logout"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF: REST API라 토큰 기반 보호 사용 안 함. 폼 로그인은 같은 도메인 한정.
                .csrf(csrf -> csrf.disable())

                // 인가 규칙
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URIS).permitAll()
                        .anyRequest().authenticated()
                )

                // 폼 로그인 (워크북 가이드)
                .formLogin(form -> form
                        .usernameParameter("email")     // 우리 도메인은 이메일로 식별
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                )

                // 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // 인증/인가 실패를 통일 응답으로
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }

    /**
     * BCrypt 기반 PasswordEncoder.
     *
     * <p>BCrypt는 솔트가 해시 안에 포함되는 알고리즘 — 별도 솔트 컬럼이 필요 없고,
     * 같은 비밀번호여도 매번 다른 해시가 나온다(레인보우 테이블·사전공격 방어).
     * 결과 길이는 항상 60자.</p>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
