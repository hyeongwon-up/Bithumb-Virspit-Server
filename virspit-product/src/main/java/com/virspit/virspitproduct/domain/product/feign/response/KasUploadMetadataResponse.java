package com.virspit.virspitproduct.domain.product.feign.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KasUploadMetadataResponse {
    private String filename;
    private String contentType;
    private String uri;
}
