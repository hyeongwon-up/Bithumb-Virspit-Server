package com.virspit.virspitservice.domain.advertisement.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AdvertisementUpdateRequestDto {
    @ApiModelProperty("광고 설명")
    private String description;

    @ApiModelProperty("상품으로 연결되는 url")
    private String url;
}
