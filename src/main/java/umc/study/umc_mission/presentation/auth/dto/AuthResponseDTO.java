package umc.study.umc_mission.presentation.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 인증/회원가입(Auth) 응답 DTO 모음.
 */
public class AuthResponseDTO {

    /** 회원가입 완료 응답 — 식별자와 이메일만 가볍게 돌려준다 (비밀번호는 절대 응답에 포함 X). */
    @Builder
    @Schema(description = "회원가입 응답")
    public record SignUpResponse(

            @Schema(description = "생성된 회원 PK", example = "1")
            Long memberId,

            @Schema(description = "회원 이메일", example = "ari@example.com")
            String email

    ) {
    }
}
