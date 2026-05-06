package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.status.GeneralSuccessCode;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/api/user/me")
    public ApiResponse<MemberResDTO.HomeResponse> getHome(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam String region,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        MemberResDTO.HomeResponse response = new MemberResDTO.HomeResponse(
                null,
                null,
                region,
                List.of(),
                false
        );

        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<MemberResDTO.JoinResponse>> join(
            @RequestBody MemberReqDTO.JoinRequest request
    ) {
        MemberResDTO.JoinResponse response = new MemberResDTO.JoinResponse(
                null,
                request.name(),
                request.email()
        );

        return ResponseEntity.status(GeneralSuccessCode.CREATED.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }
}
