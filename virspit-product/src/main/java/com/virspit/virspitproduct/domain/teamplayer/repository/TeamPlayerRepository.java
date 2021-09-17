package com.virspit.virspitproduct.domain.teamplayer.repository;

import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
}
