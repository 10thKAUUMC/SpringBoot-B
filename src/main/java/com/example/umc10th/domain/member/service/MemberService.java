package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.util.AuthMemberResolver;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final ReviewRepository reviewRepository;
    private final MemberMissionRepository memberMissionRepository;

    public MemberService(MemberRepository memberRepository, MissionRepository missionRepository,
                         ReviewRepository reviewRepository, MemberMissionRepository memberMissionRepository) {
        this.memberRepository = memberRepository;
        this.missionRepository = missionRepository;
        this.reviewRepository = reviewRepository;
        this.memberMissionRepository = memberMissionRepository;
    }

    public MemberResDTO.HomeResponse getHome(String authorization, String region, Long lastId, Integer size) {
        Member member = getCurrentMember(authorization);
        Slice<Mission> missionSlice = missionRepository.findAvailableMissionsByRegion(
                member,
                region,
                lastId,
                PageRequest.of(0, size)
        );

        List<MemberResDTO.HomeMissionResponse> missions = missionSlice.getContent().stream()
                .map(mission -> new MemberResDTO.HomeMissionResponse(
                        mission.getId(),
                        mission.getStore().getId(),
                        mission.getStore().getName(),
                        mission.getStore().getDescription(),
                        mission.getContent(),
                        mission.getDeadline(),
                        mission.getPoint()
                ))
                .toList();

        long completedMissionCount = memberMissionRepository.countByMemberIdAndStatus(
                member.getId(),
                MissionStatus.COMPLETED
        );

        return new MemberResDTO.HomeResponse(
                member.getId(),
                member.getName(),
                region,
                member.getPoint(),
                completedMissionCount,
                10L,
                missions,
                missionSlice.hasNext()
        );
    }

    public MemberResDTO.MyPageResponse getMyPage(String authorization) {
        Member member = memberRepository.findMyPageMember(AuthMemberResolver.resolveMemberId(authorization))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        long reviewCount = reviewRepository.countByMemberId(member.getId());
        long completedMissionCount = memberMissionRepository.countByMemberIdAndStatus(
                member.getId(),
                MissionStatus.COMPLETED
        );

        return new MemberResDTO.MyPageResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getPhone() != null && !member.getPhone().isBlank(),
                member.getPoint(),
                reviewCount,
                completedMissionCount
        );
    }

    @Transactional
    public MemberResDTO.JoinResponse join(MemberReqDTO.JoinRequest request) {
        memberRepository.findByEmail(request.email())
                .ifPresent(member -> {
                    throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
                });

        Member member = new Member(
                request.name(),
                request.email(),
                request.password(),
                parseGender(request.gender()),
                request.birthday(),
                request.address(),
                request.favoriteFood()
        );

        Member savedMember = memberRepository.save(member);
        return new MemberResDTO.JoinResponse(savedMember.getId(), savedMember.getName(), savedMember.getEmail());
    }

    private Member getCurrentMember(String authorization) {
        Long memberId = AuthMemberResolver.resolveMemberId(authorization);
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private Gender parseGender(String gender) {
        if (gender == null || gender.isBlank()) {
            return null;
        }

        String normalizedGender = gender.equalsIgnoreCase("FEAMALE") ? "FEMALE" : gender.toUpperCase();
        return Gender.valueOf(normalizedGender);
    }
}
