package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.enums.RoleType;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 회원-역할 매핑 엔티티.
 * 한 회원이 여러 역할(USER, ADMIN 등)을 가질 수 있다.
 * Role을 별도 테이블 대신 Enum으로 관리하여 단순화.
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private RoleType roleType = RoleType.USER;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public RoleType getRoleType() {
        return roleType;
    }
}
