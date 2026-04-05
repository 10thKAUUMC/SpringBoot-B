package umc.study.umc_mission.domain.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.member.enums.Gender;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberJpaRepository memberRepository;

    @Test
    @DisplayName("회원을 저장하고 조회할 수 있다")
    void saveMember() {
        // given
        Member member = Member.builder()
                .name("유완규")
                .nickname("wangyu")
                .email("wangyu@test.com")
                .gender(Gender.MALE)
                .birth(LocalDate.of(2002, 1, 1))
                .phoneNum("0101234567")
                .address("서울특별시")
                .build();

        // when
        Member saved = memberRepository.save(member);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("유완규");
        assertThat(saved.getPoint()).isEqualTo(0L);
        assertThat(saved.getMissionClear()).isEqualTo(0);
    }

    @Test
    @DisplayName("회원 목록을 조회할 수 있다")
    void findAllMembers() {
        // given
        memberRepository.save(Member.builder().name("유저1").email("u1@test.com").build());
        memberRepository.save(Member.builder().name("유저2").email("u2@test.com").build());

        // when & then
        assertThat(memberRepository.findAll()).hasSize(2);
    }
}
