package com.virspit.virspitproduct.domain.sports.entity;

import com.virspit.virspitproduct.domain.common.BaseEntity;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Sports extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank
    @Length(max = 20)
    @Column(length = 20, unique = true)
    private String name;

    @Setter
    private String iconUrl;

    @OneToMany(mappedBy = "sports")
    private List<TeamPlayer> teamPlayers = new ArrayList<>();

    public Sports(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }
}
