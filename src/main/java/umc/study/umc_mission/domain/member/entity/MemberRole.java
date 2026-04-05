package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 회원-역할 중간 테이블.
 * Member와 Role의 다대다(N:M) 관계를 1:N + N:1로 풀어낸다.
 */
@Entity
@Table(name = "member_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Role getRole() {
        return role;
    }
}
