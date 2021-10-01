package com.virspit.virspitservice.domain.product.entity;

import com.virspit.virspitservice.domain.product.dto.NftInfo;
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

    private String title;

    private String description;

    private Long teamPlayerId;

    private Integer price;

    private Integer count;

    private LocalDateTime startDate;

    private Boolean exhibition;

    private String nftImageUrl;

    private String detailImageUrl;

    private String contractAlias;

    private String metadataUri;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public static ProductDoc dtoToEntity(final ProductDto productDto) {
        return ProductDoc.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getCount())
                .startDate(productDto.getStartDate())
                .exhibition(productDto.getExhibition())
                .nftImageUrl(productDto.getNftImageUrl())
                .detailImageUrl(productDto.getDetailImageUrl())
                .metadataUri(productDto.getNftInfo().getMetadataUri())
                .contractAlias(productDto.getNftInfo().getContractAlias())
                .createdDate(productDto.getCreatedDate())
                .updatedDate(productDto.getUpdatedDate())
                .build();
    }

    public void setId(final String id) {
        this.id = id;
    }

}
