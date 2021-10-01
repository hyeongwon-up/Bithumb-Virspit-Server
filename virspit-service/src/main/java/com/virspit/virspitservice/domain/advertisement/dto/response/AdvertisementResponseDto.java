package com.virspit.virspitservice.domain.advertisement.dto.response;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.product.dto.ProductDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = {"id", "product", "description", "url"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AdvertisementResponseDto {
    @ApiModelProperty("광고 id")
    private String id;

    @ApiModelProperty("광고되는 상품 정보")
    private ProductDto product;

    @ApiModelProperty("광고 설명")
    private String description;

    @ApiModelProperty("상품과 연결되는 url")
    private String url;

    @ApiModelProperty("광고 생성 날짜")
    private LocalDateTime createdDate;

    @ApiModelProperty("광고 수정 날짜")
    private LocalDateTime updatedDate;

    public static AdvertisementResponseDto entityToDto(AdvertisementDoc advertisement) {
        return AdvertisementResponseDto.builder()
                .id(advertisement.getId())
                .product(advertisement.getProduct() == null ? null : ProductDto.entityToDto(advertisement.getProduct()))
                .description(advertisement.getDescription())
                .createdDate(advertisement.getCreatedDate())
                .updatedDate(advertisement.getUpdatedDate())
                .build();
    }
}
