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
public class ProductDto {
    @ApiModelProperty("상품 ID")
    private String id;

    @ApiModelProperty("상품 제목")
    private String title;

    @ApiModelProperty("상품 설명")
    private String description;

    @ApiModelProperty("종목 정보")
    private SportsInfo sportsInfo;

    @ApiModelProperty("팀/선수 정보")
    private TeamPlayerInfo teamPlayerInfo;

    @ApiModelProperty("상품 가격(Klay)")
    private Integer price;

    @ApiModelProperty("상품 수량")
    private Integer remainedCount;

    @ApiModelProperty(value = "상품 판매 시작 일", example = "2021-09-26 17:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @ApiModelProperty("상품 진열 여부")
    private Boolean exhibition;

    @ApiModelProperty("NFT 이미지 주소")
    private String nftImageUrl;

    @ApiModelProperty("상품 상세 이미지 주소")
    private String detailImageUrl;

    @ApiModelProperty("상품 NFT 정보")
    private NftInfo nftInfo;

    @ApiModelProperty(value = "상품 업데이트 일", example = "2021-09-26 17:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;

    @ApiModelProperty(value = "상품 등록 일", example = "2021-09-26 17:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    public static ProductDto entityToDto(final ProductDoc productDoc) {
        return ProductDto.builder()
                .id(productDoc.getId())
                .title(productDoc.getTitle())
                .price(productDoc.getPrice())
                .description(productDoc.getDescription())
                .remainedCount(productDoc.getRemainedCount())
                .startDateTime(productDoc.getStartDateTime())
                .exhibition(productDoc.getExhibition())
                .nftImageUrl(productDoc.getNftImageUrl())
                .detailImageUrl(productDoc.getDetailImageUrl())
                .nftInfo(new NftInfo(productDoc.getContractAlias(),
                        productDoc.getMetadataUri()))
                .sportsInfo(new SportsInfo(productDoc.getSportsId(), productDoc.getSportsName()))
                .teamPlayerInfo(new TeamPlayerInfo(productDoc.getTeamPlayerId(), productDoc.getTeamPlayerName(), productDoc.getTeamPlayerType()))
                .updatedDateTime(productDoc.getUpdatedDateTime())
                .createdDateTime(productDoc.getCreatedDateTime())
                .build();
    }

}
