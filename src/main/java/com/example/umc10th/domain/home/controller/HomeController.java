package com.example.umc10th.domain.home.controller;

import com.example.umc10th.domain.home.dto.HomeResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public ApiResponse<HomeResDTO.HomeInfoDTO> getHome(
            @RequestHeader("Authorization") String authorization,
            @RequestParam Long regionId
    ) {
        HomeResDTO.HomeInfoDTO result = new HomeResDTO.HomeInfoDTO(
                "홈 화면 테스트",
                regionId
        );

        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, result);
    }
}
