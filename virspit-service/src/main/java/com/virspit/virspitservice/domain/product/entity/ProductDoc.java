package com.virspit.virspitservice.domain.product.entity;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
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

    private String nftUri;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

//    private TeamPlayer teamPlayer; todo; 정보

    public static ProductDoc dtoToEntity(final ProductDto productDto) {
        return ProductDoc.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getCount())
                .startDate(productDto.getStartDate())
                .exhibition(productDto.getExhibition())
                .type(productDto.getType())
                .nftUri(productDto.getNftUri())
                .createdDate(productDto.getCreatedDate())
                .updatedDate(productDto.getUpdatedDate())
                .build();
    }

    public void setId(final String id) {
        this.id = id;
    }

}
