package com.virspit.virspitservice.domain.advertisement.dto;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.product.dto.ProductDto;
import lombok.*;

@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AdvertisementDto {
    private String id;

    private ProductDto product;

    private String description;

    public static AdvertisementDto entityToDto(AdvertisementDoc advertisement){
        return AdvertisementDto.builder()
                .id(advertisement.getId())
                .product(ProductDto.entityToDto(advertisement.getProduct()))
                .description(advertisement.getDescription())
                .build();
    }
}
