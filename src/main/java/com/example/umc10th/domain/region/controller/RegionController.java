package com.example.umc10th.domain.region.controller;

import com.example.umc10th.domain.region.dto.RegionReqDTO;
import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.repository.RegionRepository;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionRepository regionRepository;
    @PatchMapping("/current")
    public ApiResponse<String> changeCurrentRegion(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RegionReqDTO.ChangeCurrentRegionDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, "현재 지역 변경 성공");
    }
    @PostMapping("/test")
    public ApiResponse<Long> createTestRegion() {
        Region region = Region.builder()
                .name("화전동")
                .build();

        Region savedRegion = regionRepository.save(region);

        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, savedRegion.getRegionId());
    }
}
