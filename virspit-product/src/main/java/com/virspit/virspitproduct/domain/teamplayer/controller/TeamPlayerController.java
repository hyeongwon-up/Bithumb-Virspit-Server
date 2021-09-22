package com.virspit.virspitproduct.domain.teamplayer.controller;

import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.service.TeamPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team-player")
public class TeamPlayerController {

    private final TeamPlayerService teamPlayerService;

    @GetMapping
    public List<TeamPlayerResponseDto> getAllTeamPlayer() {
        return teamPlayerService.getAllTeamPlayer();
    }

    @GetMapping("/search")
    public List<TeamPlayerResponseDto> searchByName(
            @RequestParam Long sportsId,
            @RequestParam String name) {
        return teamPlayerService.searchByName(sportsId, name);
    }

    @GetMapping("/{teamPlayerId}")
    public TeamPlayerResponseDto getTeamPlayerById(@PathVariable Long teamPlayerId) {
        return teamPlayerService.getTeamPlayerById(teamPlayerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamPlayerResponseDto createTeamPlayer(@RequestBody @Valid TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        return teamPlayerService.createTeamPlayer(teamPlayerStoreRequestDto);
    }

    @PutMapping("/{teamPlayerId}")
    public TeamPlayerResponseDto updateTeamPlayer(@PathVariable Long teamPlayerId, @RequestBody @Valid TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        return teamPlayerService.updateTeamPlayer(teamPlayerId, teamPlayerStoreRequestDto);
    }

    @DeleteMapping("/{teamPlayerId}")
    public void deleteTeamPlayer(@PathVariable Long teamPlayerId) {
        teamPlayerService.deleteTeamPlayer(teamPlayerId);
    }
}
