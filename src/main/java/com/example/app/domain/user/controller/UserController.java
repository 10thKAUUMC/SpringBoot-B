package com.example.app.domain.user.controller;

import com.example.app.domain.user.dto.request.UserRequest;
import com.example.app.domain.user.dto.response.UserResponse;
import com.example.app.domain.user.service.UserService;
import com.example.app.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse.Profile> signUp(@Valid @RequestBody UserRequest.SignUp request) {
        return ApiResponse.ok("회원가입이 완료되었습니다.", userService.signUp(request));
    }

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse.Profile> getProfile(@PathVariable Long userId) {
        return ApiResponse.ok(userService.getProfile(userId));
    }

    @Operation(summary = "프로필 수정")
    @PatchMapping("/{userId}/profile")
    public ApiResponse<UserResponse.Profile> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequest.UpdateProfile request) {
        return ApiResponse.ok("프로필이 수정되었습니다.", userService.updateProfile(userId, request));
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/{userId}/password")
    public ApiResponse<Void> updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequest.UpdatePassword request) {
        userService.updatePassword(userId, request);
        return ApiResponse.ok();
    }
}
