package com.example.umc10th.global.config;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.repository.RegionRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class TestDataInitializer {

    @Bean
    CommandLineRunner initSwaggerTestData(TestDataService testDataService) {
        return args -> testDataService.init();
    }

    @org.springframework.stereotype.Service
    public static class TestDataService {

        private static final String TEST_EMAIL = "kim@example.com";

        private final MemberRepository memberRepository;
        private final RegionRepository regionRepository;
        private final StoreRepository storeRepository;
        private final MissionRepository missionRepository;
        private final MemberMissionRepository memberMissionRepository;

        public TestDataService(MemberRepository memberRepository, RegionRepository regionRepository,
                               StoreRepository storeRepository, MissionRepository missionRepository,
                               MemberMissionRepository memberMissionRepository) {
            this.memberRepository = memberRepository;
            this.regionRepository = regionRepository;
            this.storeRepository = storeRepository;
            this.missionRepository = missionRepository;
            this.memberMissionRepository = memberMissionRepository;
        }

        @Transactional
        public void init() {
            Member member = memberRepository.findByEmail(TEST_EMAIL)
                    .orElseGet(this::createMember);
            member.updateTestProfile("010-1234-5678", 2500L);

            if (memberMissionRepository.countByMemberId(member.getId()) > 0) {
                return;
            }

            Region region = regionRepository.save(new Region("마포구"));
            Store chineseStore = storeRepository.save(new Store(
                    region,
                    "반이학생마라탕",
                    "10:00-22:00",
                    "중식당",
                    "서울 마포구 UMC로 10"
            ));
            Store reviewStore = storeRepository.save(new Store(
                    region,
                    "가게이름",
                    "11:00-21:00",
                    "한식당",
                    "서울 마포구 리뷰로 12"
            ));

            Mission inProgressMission = missionRepository.save(new Mission(
                    chineseStore,
                    "10,000원 이상의 식사시",
                    LocalDate.now().plusDays(7),
                    500
            ));
            Mission completedMission = missionRepository.save(new Mission(
                    reviewStore,
                    "12,000원 이상의 식사를 하세요!",
                    LocalDate.now().plusDays(5),
                    500
            ));
            Mission availableMission1 = missionRepository.save(new Mission(
                    chineseStore,
                    "10,000원 이상의 식사시",
                    LocalDate.now().plusDays(7),
                    500
            ));
            Mission availableMission2 = missionRepository.save(new Mission(
                    reviewStore,
                    "12,000원 이상의 식사를 하세요!",
                    LocalDate.now().plusDays(10),
                    500
            ));

            memberMissionRepository.save(new MemberMission(member, inProgressMission, MissionStatus.IN_PROGRESS));
            memberMissionRepository.save(new MemberMission(member, completedMission, MissionStatus.COMPLETED));

            // availableMission1, availableMission2는 홈 화면의 도전 가능 미션 조회에 남겨둡니다.
        }

        private Member createMember() {
            Member member = new Member(
                    "nickname012",
                    TEST_EMAIL,
                    "pw",
                    Gender.FEMALE,
                    LocalDate.of(2025, 1, 1),
                    "마포구",
                    List.of("한식", "양식")
            );
            member.updateTestProfile("010-1234-5678", 2500L);
            return memberRepository.save(member);
        }
    }
}
