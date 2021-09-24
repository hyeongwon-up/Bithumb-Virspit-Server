package com.virspit.virspitproduct.domain.product.feign;

import com.virspit.virspitproduct.domain.product.feign.response.KasUploadAssetResponse;
import com.virspit.virspitproduct.domain.product.feign.request.KasUploadMetadataRequest;
import com.virspit.virspitproduct.domain.product.feign.response.KasUploadMetadataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "kas", url = "https://metadata-api.klaytnapi.com/v1/metadata", configuration = KasMetadataFeignConfig.class)
public interface KasMetadataFeignClient {
    @PostMapping(value = "/asset", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    KasUploadAssetResponse uploadAsset(@RequestPart(value = "file") MultipartFile file);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    KasUploadMetadataResponse uploadMetadata(@RequestBody KasUploadMetadataRequest kasUploadMetadataRequest);
}
