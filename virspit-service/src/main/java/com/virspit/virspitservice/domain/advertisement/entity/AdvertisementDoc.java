package com.virspit.virspitservice.domain.advertisement.entity;

import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "advertisements")
public class AdvertisementDoc {
    @Id
    private String id;

    private ProductDoc product;

    private String description;

    private String url;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public static AdvertisementDoc dtoToEntity(AdvertisementRequestDto requestDto, ProductDoc productDoc) {
        return AdvertisementDoc.builder()
                .product(productDoc)
                .url(requestDto.getUrl())
                .description(requestDto.getDescription())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public void update(AdvertisementRequestDto requestDto, ProductDoc product) {
        this.description = requestDto.getDescription();
        this.url = requestDto.getUrl();
        this.product = product;
        this.updatedDate = LocalDateTime.now();
    }
}
