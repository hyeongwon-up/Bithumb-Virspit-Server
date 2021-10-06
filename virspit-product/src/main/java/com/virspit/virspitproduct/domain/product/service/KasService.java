package com.virspit.virspitproduct.domain.product.service;

import com.virspit.virspitproduct.domain.product.feign.contract.KasContractFeignClient;
import com.virspit.virspitproduct.domain.product.feign.contract.request.DeployKip17ContractRequest;
import com.virspit.virspitproduct.domain.product.feign.contract.request.Kip17FeePayerOption;
import com.virspit.virspitproduct.domain.product.feign.contract.request.UserFeePayer;
import com.virspit.virspitproduct.domain.product.feign.metadata.KasMetadataFeignClient;
import com.virspit.virspitproduct.domain.product.feign.metadata.request.Metadata;
import com.virspit.virspitproduct.domain.product.feign.metadata.request.UploadMetadataRequest;
import com.virspit.virspitproduct.domain.product.feign.metadata.response.UploadAssetResponse;
import com.virspit.virspitproduct.domain.product.feign.metadata.response.UploadMetadataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class KasService {
    private static final String TOKEN_NAME = "VIRSPIT";
    private static final String TOKEN_SYMBOL = "VIRSPIT";

    private final KasContractFeignClient kasContractFeignClient;
    private final KasMetadataFeignClient kasMetadataFeignClient;

    @Value("${kas.fee-payer.krn}")
    private String feePayerKrn;

    @Value("${kas.fee-payer.address}")
    private String feePayerAddress;

    public String uploadMetadata(final String name, final String description, final MultipartFile imageFile) {
        UploadAssetResponse uploadAssetResponse = kasMetadataFeignClient.uploadAsset(imageFile);

        Metadata metadata = new Metadata(name, description, uploadAssetResponse.getUri());
        UploadMetadataRequest uploadMetadataRequest = new UploadMetadataRequest(metadata);
        UploadMetadataResponse uploadMetadataResponse = kasMetadataFeignClient.uploadMetadata(uploadMetadataRequest);

        return uploadMetadataResponse.getUri();
    }

    public String deployNftContract(final long id) {
        String contractAlias = "product-" + id + "-" + System.currentTimeMillis(); // TODO alias 이름 지정 방법 찾기

        UserFeePayer userFeePayer = new UserFeePayer(feePayerKrn, feePayerAddress);
        Kip17FeePayerOption kip17FeePayerOption = new Kip17FeePayerOption(false, userFeePayer);
        DeployKip17ContractRequest deployKip17ContractRequest = new DeployKip17ContractRequest(contractAlias, TOKEN_SYMBOL, TOKEN_NAME, kip17FeePayerOption);

        kasContractFeignClient.deployContract(deployKip17ContractRequest);
        
        return contractAlias;
    }

}
