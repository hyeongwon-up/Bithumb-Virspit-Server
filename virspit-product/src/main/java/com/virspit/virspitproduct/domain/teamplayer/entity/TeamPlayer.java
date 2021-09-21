package com.virspit.virspitproduct.domain.teamplayer.entity;

import com.virspit.virspitproduct.domain.common.BaseEntity;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPORTS_ID", nullable = false)
    private Sports sports;

    @Builder
    public TeamPlayer(String name, TeamPlayerType type, String description, Integer revenueShareRate, Sports sports) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.revenueShareRate = revenueShareRate;
        this.sports = sports;
    }

}
