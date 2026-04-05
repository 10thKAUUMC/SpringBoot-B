package umc.study.umc_mission.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.member.enums.Gender;
import umc.study.umc_mission.global.common.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 회원(Member) 엔티티.
 * DB의 member 테이블과 1:1 매핑된다.
 */
@Entity
@Table(name = "member")
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

    // === Getter 메서드 (필요한 필드만 외부에 노출) ===

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public Long getPoint() {
        return point;
    }

    public Integer getMissionClear() {
        return missionClear;
    }

    /**
     * 외부에서 리스트를 직접 수정하지 못하도록 읽기 전용 복사본을 반환한다.
     */
    public List<MemberRole> getMemberRoles() {
        return Collections.unmodifiableList(memberRoles);
    }

    public List<MemberLikes> getMemberLikes() {
        return Collections.unmodifiableList(memberLikes);
    }
}
