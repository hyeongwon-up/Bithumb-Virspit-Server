package com.virspit.virspitproduct.domain.teamplayer.controller;

import com.virspit.virspitproduct.domain.common.SuccessResponse;
import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.service.TeamPlayerService;
import com.virspit.virspitproduct.error.ErrorResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("팀/플레이어 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/team-players")
public class TeamPlayerController {

    private final TeamPlayerService teamPlayerService;

    @ApiOperation(value = "전체 팀/플레이어 목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "팀/플레이어 이름", paramType = "query"),
            @ApiImplicitParam(name = "sportsId", value = "종목 ID", paramType = "query"),
    })
    @GetMapping
    public SuccessResponse<List<TeamPlayerResponseDto>> getTeamPlayers(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long sportsId) {
        return SuccessResponse.of(teamPlayerService.getTeamPlayers(name, sportsId, pageable));
    }

    @ApiOperation("ID로 팀/플레이어 조회")
    @ApiImplicitParam(name = "teamPlayerId", value = "팀/플레이어 ID", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "존재하지 않는 ID", response = ErrorResponse.class)
    })
    @GetMapping("/{teamPlayerId}")
    public SuccessResponse<TeamPlayerResponseDto> getTeamPlayerById(@PathVariable Long teamPlayerId) {
        return SuccessResponse.of(teamPlayerService.getTeamPlayerById(teamPlayerId));
    }

    @ApiOperation("팀/플레이어 추가")
    @ApiResponses({
            @ApiResponse(code = 201, message = "success"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<TeamPlayerResponseDto> createTeamPlayer(
            @ApiParam(value = "팀/플레이어 저장 요청 바디", required = true) @RequestBody @Valid TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        return SuccessResponse.of(teamPlayerService.createTeamPlayer(teamPlayerStoreRequestDto));
    }

    @ApiOperation("팀/플레이어 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "error", response = ErrorResponse.class)
    })
    @PutMapping("/{teamPlayerId}")
    public SuccessResponse<TeamPlayerResponseDto> updateTeamPlayer(
            @ApiParam(value = "팀/플레이어 ID", required = true) @PathVariable Long teamPlayerId,
            @ApiParam(value = "팀/플레이어 수정 요청 바디", required = true) @RequestBody @Valid TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        return SuccessResponse.of(teamPlayerService.updateTeamPlayer(teamPlayerId, teamPlayerStoreRequestDto), SuccessResponse.UPDATED_MESSAGE);
    }

    @ApiOperation("팀/플레이어 삭제")
    @ApiImplicitParam(name = "teamPlayerId", value = "팀/플레이어 ID", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "deleted"),
            @ApiResponse(code = 400, message = "1.존재하지 않는 팀/플레이어 ID\n2.ID 타입 불일치", response = ErrorResponse.class)
    })
    @DeleteMapping("/{teamPlayerId}")
    public SuccessResponse<TeamPlayerResponseDto> deleteTeamPlayer(@PathVariable Long teamPlayerId) {
        return SuccessResponse.of(teamPlayerService.deleteTeamPlayer(teamPlayerId), SuccessResponse.DELETED_MESSAGE);
    }
}
