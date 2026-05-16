package umc.study.umc_mission.infrastructure.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.umc_mission.domain.auth.exception.AuthErrorCode;
import umc.study.umc_mission.domain.auth.exception.AuthException;
import umc.study.umc_mission.domain.auth.service.AuthService;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.member.repository.MemberRepository;
import umc.study.umc_mission.presentation.auth.converter.AuthConverter;
import umc.study.umc_mission.presentation.auth.dto.AuthRequestDTO;
import umc.study.umc_mission.presentation.auth.dto.AuthResponseDTO;

/**
 * {@link AuthService}의 JPA 기반 구현체.
 *
 * <p>회원가입 절차:</p>
 * <ol>
 *   <li>이메일 중복 확인 — 중복이면 {@link AuthErrorCode#DUPLICATE_EMAIL}로 실패</li>
 *   <li>요청 비밀번호를 BCrypt로 해시 — 평문은 어디에도 저장되지 않음</li>
 *   <li>Member 엔티티 빌드 후 저장</li>
 *   <li>가벼운 응답 DTO 반환 (비밀번호 미포함)</li>
 * </ol>
 *
 * <p>BCrypt의 솔트는 해시 결과 안에 포함되므로 별도 솔트 컬럼·관리 불필요.</p>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponseDTO.SignUpResponse signUp(AuthRequestDTO.SignUpRequest request) {

        if (memberRepository.existsByEmail(request.email())) {
            throw new AuthException(AuthErrorCode.DUPLICATE_EMAIL);
        }

        String hashed = passwordEncoder.encode(request.password());
        Member member = AuthConverter.toEntity(request, hashed);
        Member saved = memberRepository.save(member);

        return AuthConverter.toSignUpResponse(saved);
    }
}
