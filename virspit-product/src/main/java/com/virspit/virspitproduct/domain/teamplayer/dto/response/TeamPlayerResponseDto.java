package com.virspit.virspitproduct.domain.teamplayer.dto.response;

import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import io.swagger.models.auth.In;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TeamPlayerResponseDto {
    private final Long id;
    private final String name;
    private final TeamPlayerType type;
    private final String description;
    private final Integer revenueShareRate;
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
