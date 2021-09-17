package com.virspit.virspitproduct.domain.teamplayer.entity;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class TeamPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TeamPlayerType type;
    private String description;
    private float revenueShareRate;

    @ManyToOne
    @JoinColumn(name="SPORTS_ID")
    private Sports sports;
}
