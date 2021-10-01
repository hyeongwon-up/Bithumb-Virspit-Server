package com.virspit.virspitservice.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProductKafkaDto {
    @ApiModelProperty("상품 id")
    private String id;

    @ApiModelProperty("상품 이름")
    private String title;

    @ApiModelProperty("상품 설명")
    private String description;

    @ApiModelProperty("팀/선수 id")
    private Long teamPlayerId;

    @ApiModelProperty("상품 금액")
    private Integer price;

    @ApiModelProperty("상품 수량")
    private Integer count;

    @ApiModelProperty("상품 판매 시작 날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @ApiModelProperty("상품 전시 여부")
    private Boolean exhibition;

    @ApiModelProperty("nft image url")
    private String nftImageUrl;

    @ApiModelProperty("detail image url")
    private String detailImageUrl;

    @ApiModelProperty("상품 nftUri")
    private NftInfo nftInfo;

    @ApiModelProperty("등록 삭제 여부")
    private Event event;

    @ApiModelProperty("생성 날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

}
