package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.util.AuthMemberResolver;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    public MissionService(MemberMissionRepository memberMissionRepository, MemberRepository memberRepository) {
        this.memberMissionRepository = memberMissionRepository;
        this.memberRepository = memberRepository;
    }

    public MissionResDTO.InProgressMissionPageResponse getMyInProgressMissions(
            MissionReqDTO.InProgressMissionListRequest request
    ) {
        if (!memberRepository.existsById(request.memberId())) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        int page = request.page() == null ? 0 : request.page();
        int size = request.size() == null ? 10 : request.size();
        Page<MemberMission> memberMissionPage = memberMissionRepository.findByMemberIdAndStatusOrderByIdDesc(
                request.memberId(),
                MissionStatus.IN_PROGRESS,
                PageRequest.of(page, size)
        );

        List<MissionResDTO.MissionSummaryResponse> missions = memberMissionPage.getContent().stream()
                .map(this::toMissionSummaryResponse)
                .toList();

        return new MissionResDTO.InProgressMissionPageResponse(
                missions,
                memberMissionPage.getNumber(),
                memberMissionPage.getSize(),
                memberMissionPage.getTotalElements(),
                memberMissionPage.getTotalPages(),
                memberMissionPage.hasNext()
        );
    }

    public MissionResDTO.MissionListResponse getMyMissions(
            String authorization,
            String status,
            Long lastId,
            Integer size
    ) {
        Long memberId = AuthMemberResolver.resolveMemberId(authorization);
        List<MissionStatus> statuses = parseMissionStatuses(status);
        Slice<MemberMission> memberMissionSlice = memberMissionRepository.findMyMissionsByStatuses(
                memberId,
                statuses,
                lastId,
                PageRequest.of(0, size)
        );

        List<MissionResDTO.MissionSummaryResponse> missions = memberMissionSlice.getContent().stream()
                .map(this::toMissionSummaryResponse)
                .toList();

        return new MissionResDTO.MissionListResponse(
                missions,
                status == null || status.isBlank() ? "ALL" : status.toUpperCase(),
                memberMissionSlice.hasNext()
        );
    }

    @Transactional
    public MissionResDTO.UpdateMissionStatusResponse updateMissionStatus(
            Long userMissionId,
            MissionReqDTO.UpdateMissionStatusRequest request
    ) {
        MemberMission memberMission = memberMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MEMBER_MISSION_NOT_FOUND));
        MissionStatus status = parseMissionStatus(request.status());

        memberMission.updateStatus(status);
        return new MissionResDTO.UpdateMissionStatusResponse(memberMission.getId(), memberMission.getStatus().name());
    }

    private MissionResDTO.MissionSummaryResponse toMissionSummaryResponse(MemberMission memberMission) {
        return new MissionResDTO.MissionSummaryResponse(
                memberMission.getId(),
                memberMission.getMission().getId(),
                memberMission.getMission().getStore().getId(),
                memberMission.getMission().getStore().getName(),
                memberMission.getMission().getStore().getDescription(),
                memberMission.getMission().getContent(),
                memberMission.getMission().getDeadline(),
                memberMission.getMission().getPoint(),
                memberMission.getStatus().name(),
                toStatusLabel(memberMission.getStatus()),
                toActionLabel(memberMission.getStatus())
        );
    }

    private MissionStatus parseMissionStatus(String status) {
        try {
            return MissionStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new MissionException(MissionErrorCode.INVALID_MISSION_STATUS);
        }
    }

    private List<MissionStatus> parseMissionStatuses(String status) {
        if (status == null || status.isBlank()) {
            return List.of(MissionStatus.IN_PROGRESS, MissionStatus.COMPLETED);
        }

        if (status.equalsIgnoreCase("ALL")) {
            return List.of(MissionStatus.IN_PROGRESS, MissionStatus.COMPLETED);
        }

        List<MissionStatus> statuses = new ArrayList<>();
        statuses.add(parseMissionStatus(status));
        return statuses;
    }

    private String toStatusLabel(MissionStatus status) {
        return switch (status) {
            case IN_PROGRESS -> "진행중";
            case COMPLETED -> "성공";
            case SUCCESS_REQUESTED -> "승인요청";
        };
    }

    private String toActionLabel(MissionStatus status) {
        return switch (status) {
            case IN_PROGRESS -> "미션 도전!";
            case COMPLETED -> "리뷰 남기기";
            case SUCCESS_REQUESTED -> "승인 대기";
        };
    }
}
