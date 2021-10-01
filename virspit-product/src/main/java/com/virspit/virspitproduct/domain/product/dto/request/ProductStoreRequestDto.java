package com.virspit.virspitproduct.domain.product.dto.request;

import com.virspit.virspitproduct.domain.product.entity.NftInfo;
import com.virspit.virspitproduct.domain.product.entity.Product;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel(description = "상품 등록 및 수정 요청 DTO")
@Getter
@Setter
public class ProductStoreRequestDto {
    @ApiModelProperty("상품 제목")
    @NotBlank
    private String title;

    @ApiModelProperty("상품 설명")
    @NotBlank
    private String description;

    @ApiModelProperty("팀/플레이어 ID")
    @NotNull
    private Long teamPlayerId;

    @ApiModelProperty("상품 가격(Klay)")
    @NotNull
    @DecimalMin("1")
    private Integer price;

    @ApiModelProperty("상품 수량")
    @NotNull
    @DecimalMin("0")
    @DecimalMax("100")
    private Integer count;

    @ApiModelProperty("판매 시작 일 ex) 2021-09-26 18:00:00")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @ApiModelProperty("상품 진열 여부")
    @NotNull
    private Boolean exhibition;

    @ApiModelProperty("NFT 이미지 파일")
    private MultipartFile nftImageFile;

    @ApiModelProperty("상품 상세 이미지 파일")
    private MultipartFile detailImageFile;

    public Product toProduct(final TeamPlayer teamPlayer, final NftInfo nftInfo) {
        return Product.builder()
                .title(title)
                .description(description)
                .teamPlayer(teamPlayer)
                .nftInfo(nftInfo)
                .price(price)
                .count(count)
                .exhibition(exhibition)
                .startDateTime(startDateTime)
                .build();
    }
}
