package com.virspit.virspitproduct.domain.teamplayer.entity;

import com.virspit.virspitproduct.domain.common.BaseEntity;
import com.virspit.virspitproduct.domain.product.entity.Product;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class TeamPlayer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    private TeamPlayerType type;

    @NotBlank
    private String description;

    @Min(0)
    @Max(100)
    @NotNull
    private Integer revenueShareRate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPORTS_ID", nullable = false)
    private Sports sports;

    @OneToMany(mappedBy = "teamPlayer")
    private List<Product> products = new ArrayList<>();

    @Builder
    public TeamPlayer(String name, TeamPlayerType type, String description, Integer revenueShareRate, Sports sports) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.revenueShareRate = revenueShareRate;
        this.sports = sports;
    }

    public void updateByDto(TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        this.name = teamPlayerStoreRequestDto.getName();
        this.type = teamPlayerStoreRequestDto.getType();
        this.description = teamPlayerStoreRequestDto.getDescription();
        this.revenueShareRate = teamPlayerStoreRequestDto.getRevenueShareRate();
    }
}
