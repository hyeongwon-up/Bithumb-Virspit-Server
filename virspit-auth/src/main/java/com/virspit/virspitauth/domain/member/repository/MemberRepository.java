package com.virspit.virspitauth.domain.member.repository;

import com.virspit.virspitauth.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByEmail(String email);
    Member findByEmail(String email);
    Long deleteByUsername(String username);
}
