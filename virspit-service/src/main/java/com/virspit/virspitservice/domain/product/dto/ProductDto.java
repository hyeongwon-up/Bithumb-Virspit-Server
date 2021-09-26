package com.virspit.virspitservice.domain.product.dto;

import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.entity.Type;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProductDto {
    @ApiModelProperty("상품 id")
    private String id;

    @ApiModelProperty("상품 이름")
    private String name;

    @ApiModelProperty("상품 설명")
    private String description;

    @ApiModelProperty("상품 금액")
    private Integer price;

    @ApiModelProperty("상품 수량")
    private Integer count;

    @ApiModelProperty("상품 판매 시작 날짜")
    private LocalDateTime startDate;

    @ApiModelProperty("상품 전시 여부")
    private Boolean exhibition;

    @ApiModelProperty("상품 타입")
    private Type type;

    @ApiModelProperty("상품 nftUri")
    private String nftUri;

    @ApiModelProperty("상품 생성 날짜")
    private LocalDateTime createdDate;

    @ApiModelProperty("상품 수정 날짜")
    private LocalDateTime updatedDate;

    public static ProductDto entityToDto(final ProductDoc productDoc) {
        return ProductDto.builder()
                .id(productDoc.getId())
                .name(productDoc.getName())
                .price(productDoc.getPrice())
                .count(productDoc.getCount())
                .startDate(productDoc.getStartDate())
                .exhibition(productDoc.getExhibition())
                .type(productDoc.getType())
                .nftUri(productDoc.getNftUri())
                .createdDate(productDoc.getCreatedDate())
                .updatedDate(productDoc.getUpdatedDate())
                .build();
    }

}
