package com.virspit.virspitproduct.domain.teamplayer.dto.request;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(description = "팀/플레이어 저장 모델")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamPlayerStoreRequestDto {
    @ApiModelProperty(value = "이름", required = true)
    @NotBlank
    @Length(max = 30)
    private String name;

    @ApiModelProperty(value = "설명", required = true, position = 1)
    @NotBlank
    private String description;

    @ApiModelProperty(value = "타입", required = true, position = 2)
    @Enumerated(EnumType.STRING)
    private TeamPlayerType type;

    @ApiModelProperty(value = "수익 분배율", required = true, position = 3)
    @Min(0)
    @Max(100)
    private Integer revenueShareRate;

    @ApiModelProperty(value = "종목 ID", required = true, position = 4)
    @NotNull
    private Long sportsId;

    public TeamPlayer toTeamPlayer(final Sports sports) {
        return new TeamPlayer(name, type, description, revenueShareRate, sports);
    }
}
