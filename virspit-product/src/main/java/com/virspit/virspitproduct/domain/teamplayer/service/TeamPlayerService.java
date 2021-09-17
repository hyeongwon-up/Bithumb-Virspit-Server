package com.virspit.virspitproduct.domain.teamplayer.service;

import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamPlayerService {
    private final TeamPlayerRepository teamPlayerRepository;
}
