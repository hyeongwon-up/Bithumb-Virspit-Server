package com.virspit.virspitproduct.domain.product.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "종목 정보")
@AllArgsConstructor
@Getter
public class SportsInfo {
    @ApiModelProperty("종목 ID")
    private Long id;

    @ApiModelProperty("종목 이름")
    private String name;
}
