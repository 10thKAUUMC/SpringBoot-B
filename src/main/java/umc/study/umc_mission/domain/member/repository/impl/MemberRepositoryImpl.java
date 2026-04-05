package umc.study.umc_mission.domain.member.repository.impl;

import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.member.repository.MemberJpaRepository;
import umc.study.umc_mission.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

/**
 * MemberRepository의 JPA 구현체.
 * 내부적으로 Spring Data JPA(MemberJpaRepository)를 사용하여 DB에 접근한다.
 */
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository jpaRepository;

    public MemberRepositoryImpl(MemberJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Member save(Member member) {
        return jpaRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(Member member) {
        jpaRepository.delete(member);
    }
}
