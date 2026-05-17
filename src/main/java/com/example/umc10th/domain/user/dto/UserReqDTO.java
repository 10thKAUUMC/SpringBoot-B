package com.example.umc10th.domain.user.dto;

public class UserReqDTO {


        public record SignUpDTO(
                String nickname,
                String gender,
                String birth,
                Long regionId,
                String password,
                String email
        ) {}

        public record ChangeRegionDTO(
                Long regionId
        ) {}
}
