package com.virspit.virspitproduct.domain.sports.repository;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsRepository extends JpaRepository<Sports, Long> {

}
