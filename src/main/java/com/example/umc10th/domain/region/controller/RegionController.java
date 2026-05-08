package com.example.umc10th.domain.region.controller;

import com.example.umc10th.domain.region.dto.RegionReqDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regions")
public class RegionController {

    @PatchMapping("/current")
    public ApiResponse<String> changeCurrentRegion(
            @RequestHeader("Authorization") String authorization,
            @RequestBody RegionReqDTO.ChangeCurrentRegionDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, "현재 지역 변경 성공");
    }
}
