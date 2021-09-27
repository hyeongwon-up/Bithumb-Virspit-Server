package com.virspit.virspitproduct.domain.product.service;

import com.virspit.virspitproduct.domain.product.dto.request.ProductStoreRequestDto;
import com.virspit.virspitproduct.domain.product.dto.response.ProductResponseDto;
import com.virspit.virspitproduct.domain.product.entity.NftInfo;
import com.virspit.virspitproduct.domain.product.entity.Product;
import com.virspit.virspitproduct.domain.product.feign.contract.KasContractFeignClient;
import com.virspit.virspitproduct.domain.product.feign.contract.request.DeployKip17ContractRequest;
import com.virspit.virspitproduct.domain.product.feign.contract.request.Kip17FeePayerOption;
import com.virspit.virspitproduct.domain.product.feign.contract.request.UserFeePayer;
import com.virspit.virspitproduct.domain.product.feign.metadata.KasMetadataFeignClient;
import com.virspit.virspitproduct.domain.product.feign.metadata.request.Metadata;
import com.virspit.virspitproduct.domain.product.feign.metadata.request.UploadMetadataRequest;
import com.virspit.virspitproduct.domain.product.feign.metadata.response.UploadAssetResponse;
import com.virspit.virspitproduct.domain.product.feign.metadata.response.UploadMetadataResponse;
import com.virspit.virspitproduct.domain.product.kafka.KafkaProductProducer;
import com.virspit.virspitproduct.domain.product.repository.ProductRepository;
import com.virspit.virspitproduct.domain.product.repository.ProductRepositorySupport;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final String TOKEN_NAME = "VIRSPIT";
    private static final String TOKEN_SYMBOL = "VIRSPIT";

    private final ProductRepository productRepository;
    private final ProductRepositorySupport productRepositorySupport;
    private final TeamPlayerRepository teamPlayerRepository;

    private final KasContractFeignClient kasContractFeignClient;
    private final KasMetadataFeignClient kasMetadataFeignClient;

    private final KafkaProductProducer kafkaProductProducer;

    @Value("${kas.fee-payer.krn}")
    private String feePayerKrn;

    @Value("${kas.fee-payer.address}")
    private String feePayerAddress;

    public List<ProductResponseDto> getProducts(String keyword, Long teamPlayerId, Long sportsId, final Pageable pageable) {
        return ProductResponseDto.of(productRepositorySupport.findAll(keyword, teamPlayerId, sportsId, pageable));
    }

    public ProductResponseDto getProduct(final Long productId) {
        return ProductResponseDto.of(productRepository.findById(productId).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public ProductResponseDto createProduct(final ProductStoreRequestDto productStoreRequestDto) {
        TeamPlayer teamPlayer = teamPlayerRepository.findById(productStoreRequestDto.getTeamPlayerId()).orElseThrow(EntityNotFoundException::new);

        // deploy nft contract
        String contractAlias = "product-" + teamPlayer.getId() + "-" + System.currentTimeMillis(); // TODO alias 이름 지정 방법 찾기
        deployContract(contractAlias, false);

        // create nft metadata
        String metadataUri = createNftMetadata(productStoreRequestDto.getTitle(), productStoreRequestDto.getDescription(), productStoreRequestDto.getNftImageFile());

        NftInfo nftInfo = new NftInfo(contractAlias, metadataUri);
        Product product = productStoreRequestDto.toProduct(teamPlayer, nftInfo);

        ProductResponseDto productResponseDto = ProductResponseDto.of(productRepository.save(product));
        kafkaProductProducer.sendProduct(productResponseDto);

        return productResponseDto;
    }

    private void deployContract(final String alias, final Boolean enableGlobalFeePayer) {
        UserFeePayer userFeePayer = UserFeePayer.builder()
                .krn(feePayerKrn)
                .address(feePayerAddress)
                .build();

        Kip17FeePayerOption kip17FeePayerOption = Kip17FeePayerOption.builder()
                .enableGlobalFeePayer(enableGlobalFeePayer)
                .userFeePayer(userFeePayer)
                .build();

        DeployKip17ContractRequest deployKip17ContractRequest = DeployKip17ContractRequest.builder()
                .alias(alias)
                .name(TOKEN_NAME)
                .symbol(TOKEN_SYMBOL)
                .options(kip17FeePayerOption)
                .build();

        kasContractFeignClient.deployContract(deployKip17ContractRequest);
    }

    private String createNftMetadata(final String name, final String description, final MultipartFile nftImageFile) {
        UploadAssetResponse uploadAssetResponse = kasMetadataFeignClient.uploadAsset(nftImageFile);

        UploadMetadataRequest uploadMetadataRequest = new UploadMetadataRequest(Metadata.builder()
                .name(name)
                .description(description)
                .image(uploadAssetResponse.getUri())
                .build());

        UploadMetadataResponse uploadMetadataResponse = kasMetadataFeignClient.uploadMetadata(uploadMetadataRequest);

        return uploadMetadataResponse.getUri();
    }

    @Transactional
    public ProductResponseDto updateProduct(final Long productId, final ProductStoreRequestDto productStoreRequestDto) {
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);

        if (!product.getTeamPlayer().getId().equals(productStoreRequestDto.getTeamPlayerId())) {
            TeamPlayer teamPlayer = teamPlayerRepository.findById(productStoreRequestDto.getTeamPlayerId()).orElseThrow(EntityNotFoundException::new);
            product.setTeamPlayer(teamPlayer);
        }

        product.updateByDto(productStoreRequestDto);

        return ProductResponseDto.of(product);
    }
}
