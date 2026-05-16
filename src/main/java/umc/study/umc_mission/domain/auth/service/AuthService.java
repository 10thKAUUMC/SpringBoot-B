package umc.study.umc_mission.domain.auth.service;

import umc.study.umc_mission.presentation.auth.dto.AuthRequestDTO;
import umc.study.umc_mission.presentation.auth.dto.AuthResponseDTO;

/**
 * 인증/회원가입(Auth) 도메인 서비스 인터페이스.
 */
public interface AuthService {

    /**
     * 회원가입 수행. 이메일 중복 확인 → 비밀번호 BCrypt 해시 → Member 저장.
     *
     * @param request 회원가입 요청 (email/password/name/nickname)
     * @return 생성된 회원의 식별자와 이메일
     */
    AuthResponseDTO.SignUpResponse signUp(AuthRequestDTO.SignUpRequest request);
}
