package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.dto.UserReqDTO;
import com.example.umc10th.domain.user.dto.UserResDTO;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.enums.Gender;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User signUp(UserReqDTO.SignUpDTO request) {

        User user = User.builder()
                .email(request.email())
                .password(request.password())
                .name(request.nickname())
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2003,9,22))
                .address("서울")
                .totalPoint(0L)
                .build();

        return userRepository.save(user);
    }

    public UserResDTO.MyPageDTO getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        return new UserResDTO.MyPageDTO(
                user.getName(),
                user.getEmail(),
                user.getTotalPoint()
                );
    }
}
