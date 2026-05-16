package umc.study.umc_mission.presentation.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.study.umc_mission.domain.auth.exception.AuthSuccessCode;
import umc.study.umc_mission.domain.auth.service.AuthService;
import umc.study.umc_mission.global.apiPayload.ApiResponse;
import umc.study.umc_mission.presentation.auth.dto.AuthRequestDTO;
import umc.study.umc_mission.presentation.auth.dto.AuthResponseDTO;

/**
 * 인증/회원가입(Auth) REST 컨트롤러.
 *
 * <p>{@code /api/v1/auth/**}는 {@link umc.study.umc_mission.global.config.SecurityConfig}에서
 * Public URI로 허용된다(비로그인도 접근 가능). 로그인은 폼 로그인 필터가 처리하므로 컨트롤러는 두지 않음.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "인증/회원가입 API (Public)")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입.
     *
     * <pre>{@code
     * POST /api/v1/auth/sign-up
     * { "email": "ari@example.com", "password": "password1234", "name": "김아리", "nickname": "ari_kim" }
     *
     * → 201 Created
     * {
     *   "isSuccess": true,
     *   "code": "AUTH2010",
     *   "message": "성공적으로 회원가입했습니다.",
     *   "result": { "memberId": 1, "email": "ari@example.com" }
     * }
     * }</pre>
     */
    @PostMapping("/sign-up")
    @Operation(summary = "회원가입",
            description = "이메일/비밀번호와 기본 회원 정보로 가입. 비밀번호는 BCrypt로 솔트 처리되어 저장된다.")
    public ApiResponse<AuthResponseDTO.SignUpResponse> signUp(
            @Valid @RequestBody AuthRequestDTO.SignUpRequest request
    ) {
        AuthResponseDTO.SignUpResponse result = authService.signUp(request);
        return ApiResponse.onSuccess(AuthSuccessCode.SIGN_UP, result);
    }
}
