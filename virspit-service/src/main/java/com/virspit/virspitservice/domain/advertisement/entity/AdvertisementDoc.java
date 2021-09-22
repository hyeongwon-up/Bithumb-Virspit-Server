package com.virspit.virspitservice.domain.advertisement.entity;

import com.virspit.virspitservice.domain.advertisement.dto.AdvertisementDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "products")
public class AdvertisementDoc {
    @Id
    private String id;

    private ProductDoc product;

    private String description;

    public static AdvertisementDoc dtoToEntity(AdvertisementDto advertisementDto) {
        return AdvertisementDoc.builder()
                .id(advertisementDto.getId())
                .product(ProductDoc.dtoToEntity(advertisementDto.getProduct()))
                .description(advertisementDto.getDescription())
                .build();
    }
}
