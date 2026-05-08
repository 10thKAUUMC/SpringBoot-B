package com.example.umc10th.domain.region.dto;

import lombok.Getter;

public class RegionReqDTO {

    @Getter
    public static class ChangeCurrentRegionDTO {
        private Long regionId;
    }
}
