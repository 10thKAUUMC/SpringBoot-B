package com.example.umc10th.domain.mission.dto;

import java.util.List;

public class MissionReqDTO {

    public record CreateReviewDTO(
            Integer score,
            String content,
            List<String> image
    ) {}
}
