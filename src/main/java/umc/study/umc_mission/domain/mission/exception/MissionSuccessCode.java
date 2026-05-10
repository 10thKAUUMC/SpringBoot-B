package umc.study.umc_mission.domain.mission.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseSuccessCode;

/**
 * 미션(Mission) 도메인 전용 성공 코드.
 */
@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    /** 내 미션 목록 조회 성공. */
    GET_MY_MISSIONS(HttpStatus.OK, "MISSION2000", "내 미션 목록을 성공적으로 조회했습니다."),

    /** 지역 도전 가능 미션 목록 조회 성공. */
    GET_REGION_MISSIONS(HttpStatus.OK, "MISSION2001", "지역 미션 목록을 성공적으로 조회했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
