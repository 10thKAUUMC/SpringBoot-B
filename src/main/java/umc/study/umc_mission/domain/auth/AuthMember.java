package umc.study.umc_mission.domain.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import umc.study.umc_mission.domain.member.entity.Member;

import java.util.Collection;
import java.util.List;

/**
 * Spring Security의 {@link UserDetails}를 구현한 인증 주체.
 *
 * <p>{@code CustomUserDetailsService.loadUserByUsername} 결과로 반환되어
 * AuthenticationManager가 비밀번호 비교에 사용한다. 인증 성공 후에는
 * SecurityContext에 보관되어 컨트롤러에서 {@code @AuthenticationPrincipal}로 받을 수 있다.</p>
 *
 * <p>왜 Member 엔티티를 직접 UserDetails로 만들지 않는가:</p>
 * <ul>
 *   <li>도메인 엔티티에 보안 프레임워크(Spring Security) 의존을 침투시키지 않기 위함.</li>
 *   <li>도메인 엔티티는 비즈니스 표현, AuthMember는 인증 표현으로 책임 분리.</li>
 * </ul>
 */
public class AuthMember implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthMember(Long id, String email, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /** Member 엔티티에서 AuthMember로 변환. */
    public static AuthMember from(Member member) {
        return new AuthMember(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /** Spring Security의 "username"은 우리 도메인에서 이메일로 매핑된다 (폼 로그인 username = email). */
    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}
