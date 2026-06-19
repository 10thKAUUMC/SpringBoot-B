package com.example.umc10th.domain.mission.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum UserMissionStatus {
    CHALLENGING("도전 중"),
    COMPLETED("완료");

    private final String description;

}
