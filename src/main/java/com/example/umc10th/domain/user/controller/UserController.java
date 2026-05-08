package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.UserReqDTO;
import com.example.umc10th.domain.user.dto.UserResDTO;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.service.UserService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResDTO.SignUpResultDTO> signUp(
            @RequestBody UserReqDTO.SignUpDTO request
    ) {
        User user = userService.signUp(request);
        UserResDTO.SignUpResultDTO result =
                new UserResDTO.SignUpResultDTO(
                        user.getUserId(),
                        user.getName()
                );
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    @GetMapping("/mypage/{userId}")
    public ApiResponse<UserResDTO.MyPageDTO> getMyPage(
            @PathVariable Long userId
    ) {
        UserResDTO.MyPageDTO result = userService.getMyPage(userId);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

}
