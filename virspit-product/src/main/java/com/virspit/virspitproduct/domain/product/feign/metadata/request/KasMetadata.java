package com.virspit.virspitproduct.domain.product.feign.metadata.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class KasMetadata {
    private String name;
    private String description;
    private String image;
}
