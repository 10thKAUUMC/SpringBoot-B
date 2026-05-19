package com.example.umc10th.domain.store.controller;

import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.repository.RegionRepository;
import com.example.umc10th.domain.region.service.RegionService;
import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.dto.StoreResDTO;
import com.example.umc10th.domain.store.service.StoreService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")

public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ApiResponse<StoreResDTO.CreateStoreResultDTO> createStore(
            @RequestBody @Valid StoreReqDTO.CreateStoreDTO dto
    ) {

        return ApiResponse.onSuccess(
                GeneralSuccessSuccessCode.OK,
                storeService.createStore(dto)
        );
    }
}
