package com.example.umc10th.domain.home.dto;

public class HomeResDTO {

    public record HomeInfoDTO(
            String message,
            Long regionId
    ) {}
}
