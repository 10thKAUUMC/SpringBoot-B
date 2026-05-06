package com.example.umc10th.domain.member.dto;

import java.util.List;

public class MemberResDTO {

    public record HomeResponse(
            Long memberId,
            String name,
            String region,
            List<HomeMissionResponse> missions,
            Boolean hasNext
    ) {
    }

    public record HomeMissionResponse(
            Long missionId,
            Long storeId,
            String storeName,
            String content,
            Integer point
    ) {
    }

    public record JoinResponse(
            Long memberId,
            String name,
            String email
    ) {
    }
}
