package com.virspit.virspitservice.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("팀/플레이어 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TeamPlayerInfo {
    @ApiModelProperty("팀/플레이어 ID")
    private Long id;

    @ApiModelProperty("팀/플레이어 이름")
    private String name;
}