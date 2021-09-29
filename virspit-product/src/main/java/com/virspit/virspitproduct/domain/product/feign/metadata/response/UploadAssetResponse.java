package com.virspit.virspitproduct.domain.product.feign.metadata.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UploadAssetResponse {
    private String contentType;
    private String filename;
    private String uri;
}
