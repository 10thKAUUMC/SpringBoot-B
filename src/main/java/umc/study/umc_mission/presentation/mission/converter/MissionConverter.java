package umc.study.umc_mission.presentation.mission.converter;

import org.springframework.data.domain.Page;
import umc.study.umc_mission.domain.mission.entity.MemberMission;
import umc.study.umc_mission.domain.mission.entity.Mission;
import umc.study.umc_mission.presentation.mission.dto.MissionResponseDTO;

import java.util.List;

/**
 * Mission 엔티티/MemberMission 엔티티 → 응답 DTO 변환 정적 유틸.
 *
 * <p>Page 객체에서 콘텐츠와 페이징 메타를 평면화해 응답 DTO를 만든다.
 * 도메인 객체 그래프(member.mission.store)를 응답에 안전하게 평면화하는 책임을 가진다.</p>
 */
public final class MissionConverter {

    private MissionConverter() {
        throw new UnsupportedOperationException("정적 유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    /** 내 미션 한 행 변환. */
    public static MissionResponseDTO.MyMissionItem toMyMissionItem(MemberMission mm) {
        Mission m = mm.getMission();
        return MissionResponseDTO.MyMissionItem.builder()
                .memberMissionId(mm.getId())
                .missionId(m.getId())
                .title(m.getTitle())
                .content(m.getContent())
                .reward(m.getReward())
                .expiredAt(m.getExpiredAt())
                .storeName(m.getStore().getName())
                .state(mm.getState())
                .build();
    }

    /** 내 미션 페이지 변환. */
    public static MissionResponseDTO.MyMissionPage toMyMissionPage(Page<MemberMission> page) {
        List<MissionResponseDTO.MyMissionItem> items = page.getContent().stream()
                .map(MissionConverter::toMyMissionItem)
                .toList();

        return MissionResponseDTO.MyMissionPage.builder()
                .missions(items)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLast(page.isLast())
                .build();
    }

    /** 지역 미션 한 행 변환. */
    public static MissionResponseDTO.RegionMissionItem toRegionMissionItem(Mission m) {
        return MissionResponseDTO.RegionMissionItem.builder()
                .missionId(m.getId())
                .title(m.getTitle())
                .content(m.getContent())
                .reward(m.getReward())
                .expiredAt(m.getExpiredAt())
                .storeId(m.getStore().getId())
                .storeName(m.getStore().getName())
                .build();
    }

    /** 지역 미션 페이지 변환. */
    public static MissionResponseDTO.RegionMissionPage toRegionMissionPage(Page<Mission> page) {
        List<MissionResponseDTO.RegionMissionItem> items = page.getContent().stream()
                .map(MissionConverter::toRegionMissionItem)
                .toList();

        return MissionResponseDTO.RegionMissionPage.builder()
                .missions(items)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLast(page.isLast())
                .build();
    }
}
