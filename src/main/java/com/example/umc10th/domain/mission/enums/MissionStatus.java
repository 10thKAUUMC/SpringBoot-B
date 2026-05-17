package com.example.umc10th.domain.mission.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionStatus {
    BEFORE("진행 전"),
    ONGOING("진행 중"),
    COMPLETED("완료");

    private final String description;
}
