package com.virspit.virspitproduct.domain.product.feign.metadata.request;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class UploadMetadataRequest {
    private Metadata metadata;
}
