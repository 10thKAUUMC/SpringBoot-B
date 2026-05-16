package umc.study.umc_mission.infrastructure.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umc.study.umc_mission.domain.auth.AuthMember;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.member.repository.MemberRepository;

/**
 * Spring Security의 {@link UserDetailsService} 구현체.
 *
 * <p>{@code AuthenticationProvider}(폼 로그인의 경우 DaoAuthenticationProvider)가
 * 로그인 요청을 처리할 때 이 메서드를 호출해 DB에서 사용자 정보를 가져온다.
 * 가져온 {@link UserDetails}의 비밀번호와 요청 비밀번호를 PasswordEncoder가 비교해
 * 인증 성공 여부를 결정한다.</p>
 *
 * <p>인프라 계층에 둔 이유: {@code MemberRepository}에 의존(=실제 DB 접근)하고 Spring Security
 * 프레임워크 어노테이션과 결합되어 있으므로, 도메인이 아닌 인프라 어댑터로 분류.</p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 폼 로그인의 username(=이메일)으로 Member를 조회해 AuthMember(UserDetails)로 변환한다.
     *
     * <p>회원이 없으면 {@link UsernameNotFoundException}을 던진다. Spring Security가 이 예외를
     * {@code BadCredentialsException}으로 변환하므로, 클라이언트에는 "이메일이 없다"와
     * "비밀번호가 틀렸다"가 구분되지 않는다 → 보안상 의도된 동작(계정 enumeration 방지).</p>
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일입니다: " + email));
        return AuthMember.from(member);
    }
}
