package umc.study.umc_mission.presentation.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 인증/회원가입(Auth) 요청 DTO 모음.
 */
public class AuthRequestDTO {

    /**
     * 회원가입 요청 본문.
     *
     * <p>워크북 8주차 미션: 폼 로그인을 위한 email/password + 기본 회원 정보.
     * 비밀번호는 서비스에서 BCrypt로 솔트 처리 후 저장한다.</p>
     */
    @Schema(description = "회원가입 요청")
    public record SignUpRequest(

            @Schema(description = "이메일", example = "ari@example.com")
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            @Size(max = 30, message = "이메일은 30자 이하여야 합니다.")
            String email,

            @Schema(description = "비밀번호 (8자 이상)", example = "password1234")
            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, max = 30, message = "비밀번호는 8~30자여야 합니다.")
            String password,

            @Schema(description = "회원 실명", example = "김아리")
            @NotBlank(message = "이름은 필수입니다.")
            @Size(max = 10, message = "이름은 10자 이하여야 합니다.")
            String name,

            @Schema(description = "회원 닉네임", example = "ari_kim")
            @Size(max = 20, message = "닉네임은 20자 이하여야 합니다.")
            String nickname

    ) {
    }
}
