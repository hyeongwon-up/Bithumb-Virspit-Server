package com.virspit.virspitservice.domain.product.dto;

import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.entity.Type;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProductResponseDto {
    private String id;

    private String name;

    private String description;

    private Integer price;

    private Integer count;

    private LocalDateTime startDate;

    private Boolean exhibition;

    private Type type;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public static ProductResponseDto entityToDto(final ProductDoc productDoc) {
        return ProductResponseDto.builder()
                .id(productDoc.getId())
                .name(productDoc.getName())
                .price(productDoc.getPrice())
                .count(productDoc.getCount())
                .startDate(productDoc.getStartDate())
                .exhibition(productDoc.getExhibition())
                .type(productDoc.getType())
                .createdDate(productDoc.getCreatedDate())
                .updatedDate(productDoc.getUpdatedDate())
                .build();
    }

}
