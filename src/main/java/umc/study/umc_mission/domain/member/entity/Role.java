package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 역할(Role) 엔티티.
 * USER, ADMIN 등의 역할 정보를 저장한다.
 * BaseEntity를 상속하지 않음 — createdAt/updatedAt이 필요 없는 마스터 데이터.
 */
@Entity
@Table(name = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
