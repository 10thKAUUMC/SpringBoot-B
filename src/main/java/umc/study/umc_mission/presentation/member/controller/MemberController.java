package umc.study.umc_mission.presentation.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.study.umc_mission.domain.member.exception.MemberSuccessCode;
import umc.study.umc_mission.domain.member.service.MemberService;
import umc.study.umc_mission.global.apiPayload.ApiResponse;
import umc.study.umc_mission.presentation.member.dto.MemberRequestDTO;
import umc.study.umc_mission.presentation.member.dto.MemberResponseDTO;

/**
 * 회원(Member) 도메인 REST 컨트롤러.
 *
 * <h3>어노테이션 의미</h3>
 * <ul>
 *   <li>{@code @RestController} — {@code @Controller + @ResponseBody} 결합.
 *       메서드 반환값을 자동으로 JSON으로 직렬화해 응답 본문에 싣는다.</li>
 *   <li>{@code @RequiredArgsConstructor} — final 필드(여기선 {@code memberService})만 받는
 *       생성자를 Lombok이 자동 생성. Spring은 단일 생성자가 있으면 자동으로 주입을 위임한다(생성자 주입).</li>
 *   <li>{@code @RequestMapping("/api/v1/users")} — 이 컨트롤러의 모든 핸들러는
 *       {@code /api/v1/users}로 시작하는 URI에 매핑된다. {@code /api}는 전역 API 접두사,
 *       {@code /v1}은 API 버전 관리, {@code /users}는 자원 이름이다.</li>
 *   <li>{@code @Tag} — Swagger UI에서 이 컨트롤러의 그룹 이름과 설명을 표시한다.</li>
 * </ul>
 *
 * <h3>엔드포인트 설계 메모</h3>
 * <p>본 API는 5주차 워크북의 "예시 API 제작" 섹션을 따른다. 본래 마이페이지 조회는
 * 인증된 사용자를 대상으로 하므로 {@code GET /api/v1/users/me}가 자연스럽다.
 * 워크북에서는 9주차 JWT 도입 전까지 임시로 회원 ID를 Body에 받고 {@code POST}로 호출하기로 약속했다.
 * 9주차에서 {@code GET}으로 변경하고, 회원 ID는 토큰에서 추출하도록 리팩터링할 예정.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Member", description = "회원(Member) 도메인 API — 마이페이지 등")
public class MemberController {

    /**
     * Member 도메인 서비스 (포트 인터페이스).
     * 실제 구현체는 {@code MemberServiceImpl}이며, Spring DI 컨테이너가 주입해준다.
     */
    private final MemberService memberService;

    /**
     * 마이페이지(자기 정보) 조회 API.
     *
     * <p>요청 본문에서 받은 회원 ID로 회원을 조회해, 공개 가능한 정보를 반환한다.</p>
     *
     * <h4>요청/응답 예시</h4>
     * <pre>{@code
     * POST /api/v1/users/me
     * Content-Type: application/json
     *
     * { "id": 1 }
     *
     * → 200 OK
     * {
     *   "isSuccess": true,
     *   "code": "MEMBER2000",
     *   "message": "성공적으로 유저를 조회했습니다.",
     *   "result": {
     *     "name": "김아리",
     *     "nickname": "ari_kim",
     *     "email": "ari@example.com",
     *     "phoneNumber": "010-1234-5678",
     *     "point": 2500
     *   }
     * }
     * }</pre>
     *
     * <h4>관련 어노테이션</h4>
     * <ul>
     *   <li>{@code @PostMapping("/me")} — POST 메서드 + URI suffix "/me" 매핑.
     *       {@code @RequestMapping}과 합쳐 최종 경로는 {@code /api/v1/users/me}.</li>
     *   <li>{@code @RequestBody} — JSON 본문을 DTO로 역직렬화한다.</li>
     *   <li>{@code @Valid} — DTO에 선언된 Bean Validation 어노테이션({@code @NotNull} 등)을 강제한다.
     *       검증 실패 시 {@code MethodArgumentNotValidException}이 던져지므로,
     *       추후 전역 핸들러에 해당 예외 매핑을 추가하면 통일된 400 응답으로 변환할 수 있다.</li>
     * </ul>
     *
     * @param request 마이페이지 조회 요청 (회원 ID 포함)
     * @return 마이페이지 응답을 담은 통일 응답 객체
     */
    @PostMapping("/me")
    @Operation(
            summary = "마이페이지 조회",
            description = "회원 ID로 자기 정보를 조회한다. 9주차 JWT 도입 전까지 ID를 Body로 받는 임시 형태."
    )
    public ApiResponse<MemberResponseDTO.MyPageResponse> getMyPage(
            @Valid @RequestBody MemberRequestDTO.MyPageRequest request
    ) {
        // 1) 서비스 호출 — 비즈니스 로직(회원 조회 + 부재 시 예외)은 모두 서비스가 담당한다.
        MemberResponseDTO.MyPageResponse result = memberService.getMyPage(request.id());

        // 2) 도메인 SuccessCode를 명시해 통일 응답으로 감싼다.
        //    프론트는 isSuccess + code 조합으로 응답 처리 분기를 결정할 수 있다.
        return ApiResponse.onSuccess(MemberSuccessCode.GET_MY_PAGE, result);
    }
}
