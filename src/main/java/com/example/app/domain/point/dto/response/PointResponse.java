package com.example.app.domain.point.dto.response;

import com.example.app.domain.point.entity.Point;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PointResponse {

    @Getter
    @Builder
    public static class History {
        private Long pointId;
        private Integer amount;
        private String reason;
        private LocalDateTime createdAt;

        public static History from(Point point) {
            return History.builder()
                    .pointId(point.getPointId())
                    .amount(point.getAmount())
                    .reason(point.getReason())
                    .createdAt(point.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Total {
        private Integer totalPoint;
    }
}
