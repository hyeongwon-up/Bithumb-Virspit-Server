package com.virspit.virspitproduct.domain.teamplayer.dto.request;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamPlayerStoreRequestDto {
    @NotBlank
    @Length(max = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    private TeamPlayerType type;

    @NotBlank
    private String description;

    @Min(0)
    @Max(100)
    private Integer revenueShareRate;

    @NotNull
    private Long sportsId;

    @Builder
    public TeamPlayerStoreRequestDto(String name, TeamPlayerType type, String description, Integer revenueShareRate, Long sportsId) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.revenueShareRate = revenueShareRate;
        this.sportsId = sportsId;
    }

    public TeamPlayer toTeamPlayer(final Sports sports) {
        return new TeamPlayer(name, type, description, revenueShareRate, sports);
    }
}
