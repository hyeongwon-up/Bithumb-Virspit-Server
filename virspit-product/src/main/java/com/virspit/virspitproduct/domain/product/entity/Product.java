package com.virspit.virspitproduct.domain.product.entity;

import com.virspit.virspitproduct.domain.common.BaseEntity;
import com.virspit.virspitproduct.domain.product.dto.request.ProductStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_PLAYER_ID", nullable = false)
    private TeamPlayer teamPlayer;

    @NotNull
    @DecimalMin("1")
    @Column(nullable = false)
    private Integer price;

    @NotNull
    @DecimalMin("0")
    @DecimalMax("100")
    @Column(nullable = false)
    private Integer count;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @NotNull
    @Column(nullable = false)
    private Boolean exhibition;

    @NotNull
    private NftInfo nftInfo;

    @Builder
    public Product(String title, String description, TeamPlayer teamPlayer, Integer price, Integer count, LocalDateTime startDateTime, Boolean exhibition, NftInfo nftInfo) {
        this.title = title;
        this.description = description;
        this.teamPlayer = teamPlayer;
        this.price = price;
        this.count = count;
        this.startDateTime = startDateTime;
        this.exhibition = exhibition;
        this.nftInfo = nftInfo;
    }

    public void update(Product product) {
        this.title = product.title;
        this.description = product.description;
        this.teamPlayer = product.teamPlayer;
        this.price = product.price;
        this.count = product.count;
        this.startDateTime = product.startDateTime;
        this.exhibition = product.exhibition;
        this.nftInfo = product.nftInfo;
    }

    public void updateByDto(ProductStoreRequestDto productStoreRequestDto) {
        this.title = productStoreRequestDto.getTitle();
        this.description = productStoreRequestDto.getDescription();
        this.price = productStoreRequestDto.getPrice();
        this.count = productStoreRequestDto.getCount();
        this.startDateTime = productStoreRequestDto.getStartDateTime();
        this.exhibition = productStoreRequestDto.getExhibition();
    }
}
