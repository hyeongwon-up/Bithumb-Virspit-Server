package com.virspit.virspitproduct.domain.teamplayer.dto.response;

import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@ApiModel(description = "팀/플레이어 반환 모델")
@Getter
public class TeamPlayerResponseDto {

    @ApiModelProperty(value = "팀/플레이어 ID", required = true)
    private final Long id;

    @ApiModelProperty(value = "이름", required = true, position = 1)
    private final String name;

    @ApiModelProperty(value = "설명", required = true, position = 2)
    private final String description;

    @ApiModelProperty(value = "타입", required = true, position = 3)
    private final TeamPlayerType type;

    @ApiModelProperty(value = "수익 분배율", required = true, position = 4)
    private final Integer revenueShareRate;

    @ApiModelProperty(value = "종목 ID", required = true, position = 5)
    private final Long sportsId;

    private TeamPlayerResponseDto(final TeamPlayer teamPlayer) {
        id = teamPlayer.getId();
        name = teamPlayer.getName();
        type = teamPlayer.getType();
        description = teamPlayer.getDescription();
        revenueShareRate = teamPlayer.getRevenueShareRate();
        sportsId = teamPlayer.getSports().getId();
    }

    public static TeamPlayerResponseDto of(final TeamPlayer teamPlayer) {
        return new TeamPlayerResponseDto(teamPlayer);
    }

    public static List<TeamPlayerResponseDto> of(final List<TeamPlayer> teamPlayers) {
        return teamPlayers.stream().map(TeamPlayerResponseDto::new).collect(Collectors.toList());
    }
}
