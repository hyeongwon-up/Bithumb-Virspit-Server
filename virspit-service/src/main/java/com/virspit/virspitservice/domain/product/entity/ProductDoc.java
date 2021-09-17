package com.virspit.virspitservice.domain.product.entity;

import com.virspit.virspitservice.domain.product.dto.ProductRequestDto;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "products")
public class ProductDoc {
    @Id
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

    public static ProductDoc dtoToEntity(final ProductRequestDto productRequestDto) {
        return ProductDoc.builder()
                .id(productRequestDto.getId())
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getCount())
                .startDate(productRequestDto.getStartDate())
                .exhibition(productRequestDto.getExhibition())
                .type(productRequestDto.getType())
                .createdDate(productRequestDto.getCreatedDate())
                .updatedDate(productRequestDto.getUpdatedDate())
                .build();
    }

    public void setId(final String id) {
        this.id = id;
    }
//    private PlayerTeam playerTeam;
}
