package umc.study.umc_mission.domain.mission.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import umc.study.umc_mission.domain.mission.entity.Mission;
import umc.study.umc_mission.domain.mission.enums.MissionType;
import umc.study.umc_mission.domain.mission.repository.MissionJpaRepository;
import umc.study.umc_mission.domain.region.entity.Region;
import umc.study.umc_mission.domain.store.entity.Store;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MissionRepositoryTest {

    @Autowired
    private MissionJpaRepository missionRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("미션을 저장하고 조회할 수 있다")
    void saveMission() {
        // given
        Region region = Region.builder().name("서울").build();
        em.persist(region);

        Store store = Store.builder()
                .name("맛있는 가게")
                .region(region)
                .type("한식")
                .address("서울 강남구")
                .build();
        em.persist(store);

        Mission mission = Mission.builder()
                .store(store)
                .type(MissionType.VISIT)
                .title("가게 방문 미션")
                .content("맛있는 가게를 방문하세요!")
                .reward(1000L)
                .expiredAt(LocalDateTime.now().plusDays(7))
                .build();

        // when
        Mission saved = missionRepository.save(mission);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("가게 방문 미션");
        assertThat(saved.getReward()).isEqualTo(1000L);
        assertThat(saved.getStore().getName()).isEqualTo("맛있는 가게");
    }
}
