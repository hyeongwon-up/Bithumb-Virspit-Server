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
import com.virspit.virspitproduct.domain.product.feign.wallet.KasWalletFeignClient;
import com.virspit.virspitproduct.error.ErrorCode;
import com.virspit.virspitproduct.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.virspit.virspitproduct.domain.product.feign.wallet.TransactionReceipt.TransactionStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class KasService {
    private static final String TOKEN_NAME = "VIRSPIT";
    private static final String TOKEN_SYMBOL = "VIRSPIT";

    private final KasContractFeignClient kasContractFeignClient;
    private final KasMetadataFeignClient kasMetadataFeignClient;
    private final KasWalletFeignClient kasWalletFeignClient;

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

    public void deployNftContract(final String contractAlias) {

        UserFeePayer userFeePayer = new UserFeePayer(feePayerKrn, feePayerAddress);
        Kip17FeePayerOption kip17FeePayerOption = new Kip17FeePayerOption(false, userFeePayer);
        DeployKip17ContractRequest deployKip17ContractRequest = new DeployKip17ContractRequest(contractAlias, TOKEN_SYMBOL, TOKEN_NAME, kip17FeePayerOption);

        String transactionHash = kasContractFeignClient.deployContract(deployKip17ContractRequest).getTransactionHash();

        while (true) {
            TransactionStatus status = kasWalletFeignClient.getTransactionReceipt(transactionHash).getStatus();

            switch (status) {
                case Committed:
                    return;
                case CommitError: {
                    log.error("Deploy contract transaction failed");
                    throw new BusinessException(ErrorCode.NFT_CONTRACT_TRANSACTION_FAILED);
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.warn(e.getMessage());
            }
        }
    }

}
