package com.example.umc10th.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    @Schema(description = "회원 가입 요청")
    public record JoinRequest(
            @Schema(description = "회원 이름", example = "kim")
            String name,
            @Schema(description = "이메일", example = "kim@example.com")
            String email,
            @Schema(description = "비밀번호", example = "pw")
            String password,
            @Schema(description = "성별", example = "FEMALE", allowableValues = {"MALE", "FEMALE"})
            String gender,
            @Schema(description = "생년월일", example = "2025-01-01")
            LocalDate birthday,
            @Schema(description = "주소", example = "마포구")
            String address,
            @Schema(description = "선호 음식 목록", example = "[\"한식\", \"양식\"]")
            List<String> favoriteFood
    ) {
    }
}
