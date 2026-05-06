package com.example.umc10th.domain.member.dto;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record JoinRequest(
            String name,
            String email,
            String password,
            String gender,
            LocalDate birthday,
            String address,
            List<String> favoriteFood
    ) {
    }
}
