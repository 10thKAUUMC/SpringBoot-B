package umc.study.umc_mission.presentation.auth.converter;

import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.presentation.auth.dto.AuthRequestDTO;
import umc.study.umc_mission.presentation.auth.dto.AuthResponseDTO;

/**
 * 인증/회원가입(Auth) 변환기.
 *
 * <p>다른 도메인 컨버터와 동일하게 정적 유틸로 둔다.</p>
 */
public final class AuthConverter {

    private AuthConverter() {
        throw new UnsupportedOperationException("정적 유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    /**
     * 회원가입 요청 + 해시된 비밀번호로 Member 엔티티 생성.
     *
     * <p>비밀번호 인코딩 책임은 서비스(또는 호출자)에 둔다 — 컨버터는 "이미 준비된 값으로 빌더 호출"만 한다.
     * (인코더가 컨버터에 들어오면 인코더 교체 시 컨버터까지 영향을 받음.)</p>
     */
    public static Member toEntity(AuthRequestDTO.SignUpRequest request, String encodedPassword) {
        return Member.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.name())
                .nickname(request.nickname())
                .build();
    }

    /** 저장된 Member 엔티티를 회원가입 응답 DTO로 변환. */
    public static AuthResponseDTO.SignUpResponse toSignUpResponse(Member member) {
        return AuthResponseDTO.SignUpResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .build();
    }
}
