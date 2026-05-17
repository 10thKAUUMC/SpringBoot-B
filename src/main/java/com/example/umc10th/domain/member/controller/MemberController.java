package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.status.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Member", description = "회원, 홈 화면, 마이페이지 관련 API")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/user/me")
    @Operation(
            summary = "홈 화면 조회",
            description = "홈 화면 상단의 지역, 포인트, 미션 달성 현황과 현재 지역에서 도전 가능한 미션 목록을 스크롤 기반으로 조회합니다."
    )
    public ApiResponse<MemberResDTO.HomeResponse> getHome(
            @Parameter(description = "Bearer access token. 임시 구현에서는 Bearer 뒤 숫자를 회원 ID로 사용합니다.", example = "Bearer 1")
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Parameter(description = "사용자가 선택한 지역", example = "마포구")
            @RequestParam String region,
            @Parameter(description = "마지막으로 조회한 미션 ID. 첫 조회 시 생략합니다.", example = "20")
            @RequestParam(required = false) Long lastId,
            @Parameter(description = "한 번에 조회할 미션 개수", example = "10")
            @RequestParam(defaultValue = "10") Integer size
    ) {
        MemberResDTO.HomeResponse response = memberService.getHome(authorization, region, lastId, size);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/api/user/mypage")
    @Operation(
            summary = "마이페이지 조회",
            description = "마이페이지 화면에 필요한 닉네임, 이메일, 휴대폰 인증 여부, 포인트, 작성 리뷰 수, 완료 미션 수를 조회합니다."
    )
    public ApiResponse<MemberResDTO.MyPageResponse> getMyPage(
            @Parameter(description = "Bearer access token. 임시 구현에서는 Bearer 뒤 숫자를 회원 ID로 사용합니다.", example = "Bearer 1")
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        return ApiResponse.onSuccess(memberService.getMyPage(authorization));
    }

    @PostMapping("/users")
    @Operation(
            summary = "회원 가입",
            description = "소셜 로그인을 제외한 일반 회원 가입을 진행합니다."
    )
    public ResponseEntity<ApiResponse<MemberResDTO.JoinResponse>> join(
            @RequestBody MemberReqDTO.JoinRequest request
    ) {
        MemberResDTO.JoinResponse response = memberService.join(request);

        return ResponseEntity.status(GeneralSuccessCode.CREATED.getStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }
}
