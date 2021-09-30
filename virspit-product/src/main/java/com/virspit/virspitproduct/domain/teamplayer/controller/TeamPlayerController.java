package com.virspit.virspitproduct.domain.teamplayer.controller;

import com.virspit.virspitproduct.domain.common.SuccessResponse;
import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.service.TeamPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public SuccessResponse<List<TeamPlayerResponseDto>> getTeamPlayers(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long sportsId) {
        return SuccessResponse.of(teamPlayerService.getTeamPlayers(name, sportsId, pageable));
    }

    @GetMapping("/{teamPlayerId}")
    public SuccessResponse<TeamPlayerResponseDto> getTeamPlayerById(@PathVariable Long teamPlayerId) {
        return SuccessResponse.of(teamPlayerService.getTeamPlayerById(teamPlayerId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<TeamPlayerResponseDto> createTeamPlayer(@RequestBody @Valid TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        return SuccessResponse.of(teamPlayerService.createTeamPlayer(teamPlayerStoreRequestDto));
    }

    @PutMapping("/{teamPlayerId}")
    public SuccessResponse<TeamPlayerResponseDto> updateTeamPlayer(@PathVariable Long teamPlayerId, @RequestBody @Valid TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        return SuccessResponse.of(teamPlayerService.updateTeamPlayer(teamPlayerId, teamPlayerStoreRequestDto));
    }

    @DeleteMapping("/{teamPlayerId}")
    public SuccessResponse<TeamPlayerResponseDto> deleteTeamPlayer(@PathVariable Long teamPlayerId) {
        return SuccessResponse.of(teamPlayerService.deleteTeamPlayer(teamPlayerId), SuccessResponse.DELETED_MESSAGE);
    }
}
