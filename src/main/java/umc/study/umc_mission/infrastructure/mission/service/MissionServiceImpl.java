package umc.study.umc_mission.infrastructure.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.umc_mission.domain.member.exception.MemberErrorCode;
import umc.study.umc_mission.domain.member.exception.MemberException;
import umc.study.umc_mission.domain.member.repository.MemberRepository;
import umc.study.umc_mission.domain.mission.entity.MemberMission;
import umc.study.umc_mission.domain.mission.entity.Mission;
import umc.study.umc_mission.domain.mission.enums.MissionState;
import umc.study.umc_mission.domain.mission.exception.MissionErrorCode;
import umc.study.umc_mission.domain.mission.exception.MissionException;
import umc.study.umc_mission.domain.mission.repository.MemberMissionRepository;
import umc.study.umc_mission.domain.mission.repository.MissionRepository;
import umc.study.umc_mission.domain.mission.service.MissionService;
import umc.study.umc_mission.domain.region.repository.RegionRepository;
import umc.study.umc_mission.presentation.mission.converter.MissionConverter;
import umc.study.umc_mission.presentation.mission.dto.MissionResponseDTO;

/**
 * MissionService의 JPA 기반 구현체.
 *
 * <p>두 화면을 책임진다.</p>
 * <ul>
 *   <li>내 미션 목록 — {@link MemberMissionRepository#findByMemberAndState}로 페이징</li>
 *   <li>지역 미션 목록 — {@link MissionRepository#findChallengableByRegion}로 페이징</li>
 * </ul>
 *
 * <p>두 메서드 모두 SELECT만 수행하므로 클래스 디폴트 {@code readOnly = true}로 충분하다.</p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;

    /**
     * 내 미션 목록 조회.
     *
     * <p>회원이 존재하지 않으면 MemberException으로 명확히 실패시킨다.
     * (없는 회원의 페이징 결과를 빈 페이지로 돌려보내면 클라이언트가 잘못된 ID를 의식하지 못한다.)</p>
     */
    @Override
    public MissionResponseDTO.MyMissionPage getMyMissions(Long memberId, MissionState state, Pageable pageable) {
        if (memberRepository.findById(memberId).isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        Page<MemberMission> page = memberMissionRepository.findByMemberAndState(memberId, state, pageable);
        return MissionConverter.toMyMissionPage(page);
    }

    /**
     * 지역 도전 가능 미션 목록 조회.
     *
     * <p>지역이 존재하지 않으면 MissionException(REGION_NOT_FOUND)로 실패.</p>
     */
    @Override
    public MissionResponseDTO.RegionMissionPage getMissionsByRegion(Long regionId, Pageable pageable) {
        if (regionRepository.findById(regionId).isEmpty()) {
            throw new MissionException(MissionErrorCode.REGION_NOT_FOUND);
        }

        Page<Mission> page = missionRepository.findChallengableByRegion(regionId, pageable);
        return MissionConverter.toRegionMissionPage(page);
    }
}
