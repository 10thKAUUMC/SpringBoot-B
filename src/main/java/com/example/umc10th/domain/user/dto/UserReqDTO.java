package com.example.umc10th.domain.user.dto;

import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserReqDTO {


        public record SignUpDTO(
                @NotBlank(message = "닉네임은 필수입니다.")
                String nickname,

                @NotNull(message = "성별은 필수입니다.")
                Gender gender,

                @NotNull(message = "생년월일은 필수입니다.")
                LocalDate birth,

                @NotNull(message = "지역 ID는 필수입니다.")
                Long regionId,

                @NotBlank(message = "비밀번호는 필수입니다.")
                @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
                String password,

                @NotBlank(message = "이메일은 필수입니다.")
                @Email(message = "올바른 이메일 형식이 아닙니다.")
                String email
        ) {}

        public record LoginDTO(

                @NotBlank(message = "이메일은 필수입니다.")
                @Email(message = "올바른 이메일 형식이 아닙니다.")
                String email,

                @NotBlank(message = "비밀번호는 필수입니다.")
                String password

        ) {}

        public record ChangeRegionDTO(
                Long regionId
        ) {}
}
