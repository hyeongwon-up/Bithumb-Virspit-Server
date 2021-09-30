package com.virspit.virspitproduct.domain.teamplayer.repository;

import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
}
