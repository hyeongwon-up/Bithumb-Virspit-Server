package com.virspit.virspitproduct.domain.product.entity;

import com.virspit.virspitproduct.domain.common.BaseEntity;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String description;

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

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();
}
