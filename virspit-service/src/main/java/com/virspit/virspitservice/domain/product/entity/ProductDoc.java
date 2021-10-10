package com.virspit.virspitservice.domain.product.entity;

import com.virspit.virspitservice.domain.product.dto.*;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

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

    private String teamPlayerName;

    private String teamPlayerType;

    private Long sportsId;

    private String sportsName;

    private Integer price;

    private Integer remainedCount;

    private LocalDateTime startDateTime;

    private Boolean exhibition;

    private String nftImageUrl;

    private String detailImageUrl;

    private String contractAlias;

    private String metadataUri;

    private LocalDateTime createdDateTime;

    private LocalDateTime updateDateTime;

    public static ProductDoc kafkaToEntity(final ProductKafkaDto productDto) {
        return ProductDoc.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .remainedCount(productDto.getRemainedCount())
                .startDateTime(productDto.getStartDateTime())
                .exhibition(productDto.getExhibition())
                .nftImageUrl(productDto.getNftImageUrl())
                .detailImageUrl(productDto.getDetailImageUrl())
                .metadataUri(Optional.ofNullable(productDto.getNftInfo())
                        .map(NftInfo::getMetadataUri).orElse(null))
                .contractAlias(Optional.ofNullable(productDto.getNftInfo())
                        .map(NftInfo::getContractAlias).orElse(null))
                .teamPlayerId(Optional.ofNullable(productDto.getTeamPlayerInfo())
                        .map(TeamPlayerInfo::getId).orElse(null))
                .teamPlayerName(Optional.ofNullable(productDto.getTeamPlayerInfo())
                        .map(TeamPlayerInfo::getName).orElse(null))
                .teamPlayerType(Optional.ofNullable(productDto.getTeamPlayerInfo())
                        .map(TeamPlayerInfo::getType).orElse(null))
                .sportsId(Optional.ofNullable(productDto.getSportsInfo())
                        .map(SportsInfo::getId).orElse(null))
                .sportsName(Optional.ofNullable(productDto.getSportsInfo())
                        .map(SportsInfo::getName).orElse(null))
                .createdDateTime(productDto.getCreatedDateTime())
                .updateDateTime(productDto.getUpdateDateTime())
                .build();
    }

    public void setId(final String id) {
        this.id = id;
    }

}
