package umc.study.umc_mission.domain.review.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.review.entity.Review;
import umc.study.umc_mission.infrastructure.review.repository.ReviewJpaRepository;
import umc.study.umc_mission.domain.region.entity.Region;
import umc.study.umc_mission.domain.store.entity.Store;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Autowired
    private ReviewJpaRepository reviewRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("리뷰를 저장하고 조회할 수 있다")
    void saveReview() {
        // given
        Member member = Member.builder().name("테스트").email("test@test.com").build();
        em.persist(member);

        Region region = Region.builder().name("서울").build();
        em.persist(region);

        Store store = Store.builder().name("가게").region(region).build();
        em.persist(store);

        Review review = Review.builder()
                .member(member)
                .store(store)
                .rating("5")
                .content("맛있어요!")
                .build();

        // when
        Review saved = reviewRepository.save(review);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRating()).isEqualTo("5");
        assertThat(saved.getMember().getName()).isEqualTo("테스트");
    }
}
