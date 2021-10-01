package com.virspit.virspitproduct.domain.product.feign.metadata.request;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Metadata {
    private String name;
    private String description;
    private String image;
}
