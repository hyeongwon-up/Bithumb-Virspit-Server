package com.virspit.virspitauth.domain.member.repository;

import com.virspit.virspitauth.domain.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Integer> {
    boolean existsByEmail(String email);
    Member findByEmail(String email);
    Long deleteByUsername(String username);
}
