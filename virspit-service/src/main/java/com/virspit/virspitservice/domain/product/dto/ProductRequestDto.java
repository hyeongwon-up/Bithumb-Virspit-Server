package com.virspit.virspitservice.domain.product.dto;

import com.virspit.virspitservice.domain.product.entity.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
    private String id;

    private String name;

    private String description;

    private Integer price;

    private Integer count;

    private LocalDateTime startDate;

    private Boolean exhibition;

    private Type type;
}
