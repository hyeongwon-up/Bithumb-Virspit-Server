package com.virspit.virspitservice.domain.product.entity;

import com.virspit.virspitservice.domain.common.BaseEntity;
import com.virspit.virspitservice.domain.teamPlayer.entity.TeamPlayer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_player_id")
    private TeamPlayer teamPlayer;

    private Integer price;

    private Integer count;

    private LocalDateTime startDate;

    private Boolean exhibition;
}
