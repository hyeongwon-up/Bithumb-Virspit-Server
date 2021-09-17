package com.virspit.virspitservice.domain.product.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collation = "product")
public class ProductDoc {
    @Id
    private String id;

    private String name;

    private String description;

    private Integer price;

    private Integer count;

    private LocalDateTime startDate;

    private Boolean exhibition;

    private Type type;
//    private PlayerTeam playerTeam;
}
