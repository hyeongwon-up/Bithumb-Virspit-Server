package com.virspit.virspitservice.domain.product.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ProductResponseDto {
    private String id;

    private String name;

    private String description;

    private Integer price;

    private Integer count;

    private LocalDateTime startDate;

    private Boolean exhibition;
}
