package com.virspit.virspitproduct.domain.product.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.virspit.virspitproduct.domain.product.entity.NftInfo;
import com.virspit.virspitproduct.domain.product.entity.Product;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "ProductResponseDto", description = "상품 응답 DTO")
@Getter
public class ProductKafkaDto {
    private final KafkaEvent event;

    @ApiModelProperty("상품 ID")
    private final Long id;

    @ApiModelProperty("상품 제목")
    private final String title;

    @ApiModelProperty("상품 설명")
    private final String description;

    @ApiModelProperty("종목 정보")
    private final SportsInfo sportsInfo;

    @ApiModelProperty("팀/선수 정보")
    private final TeamPlayerInfo teamPlayerInfo;

    @ApiModelProperty("상품 가격(Klay)")
    private final Integer price;

    @ApiModelProperty("상품 수량")
    private final Integer remainedCount;

    @ApiModelProperty(value = "상품 판매 시작 일", example = "2021-09-26 17:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startDateTime;

    @ApiModelProperty("상품 진열 여부")
    private final Boolean exhibition;

    @ApiModelProperty("NFT 이미지 주소")
    private final String nftImageUrl;

    @ApiModelProperty("상품 상세 이미지 주소")
    private final String detailImageUrl;

    @ApiModelProperty("상품 NFT 정보")
    private final NftInfo nftInfo;

    @ApiModelProperty(value = "상품 업데이트 일", example = "2021-09-26 17:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedDateTime;

    @ApiModelProperty(value = "상품 등록 일", example = "2021-09-26 17:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdDateTime;

    public ProductKafkaDto(final Product product, final KafkaEvent event) {
        this.event = event;
        id = product.getId();
        title = product.getTitle();
        description = product.getDescription();
        price = product.getPrice();
        remainedCount = product.getRemainedCount();
        exhibition = product.getExhibition();
        nftImageUrl = product.getNftImageUrl();
        detailImageUrl = product.getDetailImageUrl();
        nftInfo = product.getNftInfo();
        startDateTime = product.getStartDateTime();
        updatedDateTime = product.getUpdatedDateTime();
        createdDateTime = product.getCreatedDateTime();

        TeamPlayer teamPlayer = product.getTeamPlayer();
        teamPlayerInfo = new TeamPlayerInfo(teamPlayer.getId(), teamPlayer.getName(),teamPlayer.getType());

        Sports sports = teamPlayer.getSports();
        sportsInfo = new SportsInfo(sports.getId(), sports.getName());
    }
}
