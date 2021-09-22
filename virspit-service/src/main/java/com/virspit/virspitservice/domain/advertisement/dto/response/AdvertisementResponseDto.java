package com.virspit.virspitservice.domain.advertisement.dto.response;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.product.dto.ProductDto;
import lombok.*;

@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AdvertisementResponseDto {
    private String id;

    private ProductDto product;

    private String description;

    public static AdvertisementResponseDto entityToDto(AdvertisementDoc advertisement){
        return AdvertisementResponseDto.builder()
                .id(advertisement.getId())
                .product(ProductDto.entityToDto(advertisement.getProduct()))
                .description(advertisement.getDescription())
                .build();
    }
}
