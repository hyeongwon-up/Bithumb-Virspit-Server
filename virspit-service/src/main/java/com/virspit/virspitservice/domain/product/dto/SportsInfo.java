package com.virspit.virspitservice.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("종목 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SportsInfo {
    @ApiModelProperty("종목 ID")
    private Long id;

    @ApiModelProperty("종목 이름")
    private String name;
}