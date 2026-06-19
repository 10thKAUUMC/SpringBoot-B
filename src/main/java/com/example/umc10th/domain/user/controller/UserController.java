package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.UserReqDTO;
import com.example.umc10th.domain.user.dto.UserResDTO;
import com.example.umc10th.domain.user.exception.UserSuccessCode;
import com.example.umc10th.domain.user.service.UserService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessSuccessCode;
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/v1/users/signup")
    public ApiResponse<UserResDTO.SignUpResultDTO> signUp(
            @RequestBody @Valid UserReqDTO.SignUpDTO request
    ) {
        return ApiResponse.onSuccess(UserSuccessCode.SIGNUP_SUCCESS,userService.signUp(request));
    }

    @PostMapping("/v1/users/login")
    public ApiResponse<UserResDTO.LoginResultDTO> login(
            @RequestBody @Valid UserReqDTO.LoginDTO request
    ) {
        return ApiResponse.onSuccess(UserSuccessCode.LOGIN_SUCCESS,userService.login(request));
    }

    @GetMapping("/v1/users/mypage/{userId}")
    public ApiResponse<UserResDTO.MyPageDTO> getMyPage(
            @PathVariable Long userId
    ) {
        UserResDTO.MyPageDTO result = userService.getMyPage(userId);

        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, result);
    }
    @GetMapping("/v2/users/mypage")
    public ApiResponse<UserResDTO.MyPageDTO> getMyPage(
            @AuthenticationPrincipal UserDetails userDetails
            ){
        String email = userDetails.getUsername();

        UserResDTO.MyPageDTO result = userService.getMyPageByEmail(email);

        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, result);
    }



}
