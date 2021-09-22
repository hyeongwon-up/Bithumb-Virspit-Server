package com.virspit.virspitservice.domain.advertisement.entity;

import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
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

    public static AdvertisementDoc dtoToEntity(AdvertisementResponseDto advertisementResponseDto) {
        return AdvertisementDoc.builder()
                .id(advertisementResponseDto.getId())
                .product(ProductDoc.dtoToEntity(advertisementResponseDto.getProduct()))
                .description(advertisementResponseDto.getDescription())
                .build();
    }

    public static AdvertisementDoc dtoToEntity(AdvertisementRequestDto requestDto, ProductDoc productDoc) {
        return AdvertisementDoc.builder()
                .product(productDoc)
                .description(requestDto.getDescription())
                .build();
    }
}
