package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("""
            select m
            from Member m
            where m.id = :memberId
            """)
    Optional<Member> findMyPageMember(@Param("memberId") Long memberId);
}
