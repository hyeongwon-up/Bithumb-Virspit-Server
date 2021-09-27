package com.virspit.virspitproduct.domain.product.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.virspit.virspitproduct.domain.product.entity.NftInfo;
import com.virspit.virspitproduct.domain.product.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "ProductResponseDto", description = "상품 응답 DTO")
@Getter
public class ProductResponseDto {
    @ApiModelProperty("상품 ID")
    private final Long id;

    @ApiModelProperty("상품 제목")
    @NotBlank
    private final String title;

    @ApiModelProperty("상품 설명")
    @NotBlank
    private final String description;

    @ApiModelProperty("팀/플레이어 ID")
    @NotNull
    private final Long teamPlayerId;

    @ApiModelProperty("상품 가격(Klay)")
    @NotNull
    @DecimalMin("1")
    private final Integer price;

    @ApiModelProperty("상품 수량")
    @NotNull
    @DecimalMin("0")
    @DecimalMax("100")
    private final Integer count;

    @ApiModelProperty(value = "상품 판매 시작 일", example = "2021-09-26 17:00:00")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startDateTime;

    @ApiModelProperty("상품 진열 여부")
    @NotNull
    private final Boolean exhibition;

    @ApiModelProperty("상품 NFT 정보")
    @NotNull
    private final NftInfo nftInfo;

    private ProductResponseDto(Product product) {
        id = product.getId();
        title = product.getTitle();
        description = product.getDescription();
        teamPlayerId = product.getTeamPlayer().getId();
        price = product.getPrice();
        count = product.getCount();
        startDateTime = product.getStartDateTime();
        exhibition = product.getExhibition();
        nftInfo = product.getNftInfo();
    }

    public static List<ProductResponseDto> of(final List<Product> products) {
        return products.stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    public static ProductResponseDto of(final Product product) {
        return new ProductResponseDto(product);
    }
}
