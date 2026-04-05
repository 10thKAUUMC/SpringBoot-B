package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.enums.Gender;
import umc.study.umc_mission.global.common.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(length = 20)
    private String nickname;

    @Column(nullable = false, length = 30)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private LocalDate birth;

    @Column(length = 15)
    private String phoneNum;

    @Column(length = 50)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private Long point = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Integer missionClear = 0;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberRole> memberRoles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberLikes> memberLikes = new ArrayList<>();
}
