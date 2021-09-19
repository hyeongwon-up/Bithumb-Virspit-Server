package com.virspit.virspitproduct.domain.sports.entity;

import com.virspit.virspitproduct.domain.common.BaseEntity;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Sports extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true)
    private String name;
    private String iconUrl;

    @OneToMany(mappedBy = "sports")
    private List<TeamPlayer> teamPlayers = new ArrayList<>();

    public Sports(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }
}
