package com.example.umc10th.domain.user.dto;

import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.user.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserReqDTO {


        public record SignUpDTO(
                String nickname,
                Gender gender,
                LocalDate birth,
                String regionId,
                String password,
                String email
        ) {}

        public record ChangeRegionDTO(
                Long regionId
        ) {}
}
