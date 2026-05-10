package umc.study.umc_mission.domain.mission.service;

import org.springframework.data.domain.Pageable;
import umc.study.umc_mission.domain.mission.enums.MissionState;
import umc.study.umc_mission.presentation.mission.dto.MissionResponseDTO;

/**
 * Mission(미션) 도메인 서비스 인터페이스.
 *
 * <p>두 화면을 지원한다:
 * <ul>
 *   <li>내 미션 목록 화면 — 회원의 진행 중/완료 미션</li>
 *   <li>홈(지역) 화면 — 선택한 지역에서 도전 가능한 미션</li>
 * </ul>
 * </p>
 */
public interface MissionService {

    /**
     * 회원의 미션 참여 기록을 상태별로 페이징 조회한다.
     *
     * @param memberId 회원 PK
     * @param state    필터링 상태(CHALLENGING, COMPLETE)
     * @param pageable 페이징 파라미터
     */
    MissionResponseDTO.MyMissionPage getMyMissions(Long memberId, MissionState state, Pageable pageable);

    /**
     * 특정 지역에 속한 가게들의 도전 가능한 미션을 페이징 조회한다.
     *
     * @param regionId 지역 PK
     * @param pageable 페이징 파라미터
     */
    MissionResponseDTO.RegionMissionPage getMissionsByRegion(Long regionId, Pageable pageable);
}
