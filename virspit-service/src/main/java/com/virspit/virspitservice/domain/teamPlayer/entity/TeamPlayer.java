package com.virspit.virspitservice.domain.teamPlayer.entity;

import com.virspit.virspitservice.domain.product.entity.Product;
import com.virspit.virspitservice.domain.sports.entity.Sports;
import com.virspit.virspitservice.domain.type.entity.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Document("teams_players") // todo : mongodb - jpa
public class TeamPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String description;

    private Float rate;

    @OneToMany(mappedBy = "teamPlayer", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_id")
    private Sports sports;
}
