package com.virspit.virspitproduct.domain.teamplayer.controller;

import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.service.TeamPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team-player")
public class TeamPlayerController {

    private final TeamPlayerService teamPlayerService;

}
