package com.virspit.virspitproduct.domain.product.feign.metadata;

import com.virspit.virspitproduct.domain.product.feign.KasFeignConfig;
import com.virspit.virspitproduct.domain.product.feign.metadata.response.UploadAssetResponse;
import com.virspit.virspitproduct.domain.product.feign.metadata.request.UploadMetadataRequest;
import com.virspit.virspitproduct.domain.product.feign.metadata.response.UploadMetadataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "metadata", url = "https://metadata-api.klaytnapi.com/v1/metadata", configuration = KasFeignConfig.class)
public interface KasMetadataFeignClient {
    @PostMapping(value = "/asset", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadAssetResponse uploadAsset(@RequestPart(value = "file") MultipartFile file);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    UploadMetadataResponse uploadMetadata(@RequestBody UploadMetadataRequest uploadMetadataRequest);
}
