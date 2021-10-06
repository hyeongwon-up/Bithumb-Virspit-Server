package com.virspit.virspitproduct.domain.product.service;

import com.querydsl.core.QueryResults;
import com.virspit.virspitproduct.domain.common.PagingResponseDto;
import com.virspit.virspitproduct.domain.product.dto.request.ProductStoreRequestDto;
import com.virspit.virspitproduct.domain.product.dto.response.KafkaEvent;
import com.virspit.virspitproduct.domain.product.dto.response.ProductKafkaDto;
import com.virspit.virspitproduct.domain.product.dto.response.ProductResponseDto;
import com.virspit.virspitproduct.domain.product.entity.NftInfo;
import com.virspit.virspitproduct.domain.product.entity.Product;
import com.virspit.virspitproduct.domain.product.exception.ProductNotFoundException;
import com.virspit.virspitproduct.domain.product.kafka.KafkaProductProducer;
import com.virspit.virspitproduct.domain.product.repository.ProductRepository;
import com.virspit.virspitproduct.domain.product.repository.ProductRepositorySupport;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.exception.TeamPlayerNotFoundException;
import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepository;
import com.virspit.virspitproduct.util.file.ContentType;
import com.virspit.virspitproduct.util.file.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductRepositorySupport productRepositorySupport;
    private final TeamPlayerRepository teamPlayerRepository;
    private final KasService kasService;
    private final KafkaProductProducer kafkaProductProducer;
    private final FileStore awsS3FileStore;

    public PagingResponseDto<ProductResponseDto> getProducts(String keyword, Long teamPlayerId, Long sportsId, Boolean isTeam, final Pageable pageable) {
        QueryResults<Product> queryResults = productRepositorySupport.findAll(keyword, teamPlayerId, sportsId, isTeam, pageable);
        return new PagingResponseDto<>(queryResults.getTotal(), ProductResponseDto.of(queryResults.getResults()));
    }

    public ProductResponseDto getProduct(final Long productId) {
        return ProductResponseDto.of(productRepository.findById(productId).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public ProductResponseDto createProduct(final ProductStoreRequestDto productStoreRequestDto) throws IOException {
        final Long teamPlayerId = productStoreRequestDto.getTeamPlayerId();

        TeamPlayer teamPlayer = teamPlayerRepository.findById(teamPlayerId)
                .orElseThrow(() -> new TeamPlayerNotFoundException(teamPlayerId));

        String contractAlias = kasService.deployNftContract(teamPlayerId);
        String metadataUri = kasService.uploadMetadata(productStoreRequestDto.getTitle(), productStoreRequestDto.getDescription(), productStoreRequestDto.getNftImageFile());
        NftInfo nftInfo = new NftInfo(contractAlias, metadataUri);

        String nftImageUrl = awsS3FileStore.uploadFile(productStoreRequestDto.getNftImageFile(), ContentType.PRODUCT_NFT_IMAGE);
        String detailImageUrl = awsS3FileStore.uploadFile(productStoreRequestDto.getDetailImageFile(), ContentType.PRODUCT_DETAIL_IMAGE);

        Product product = productStoreRequestDto.toProduct(teamPlayer, nftInfo, nftImageUrl, detailImageUrl);
        productRepository.save(product);

        kafkaProductProducer.sendProduct(new ProductKafkaDto(product, KafkaEvent.UPDATE));

        return ProductResponseDto.of(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(final Long productId, final ProductStoreRequestDto productStoreRequestDto) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);

        if (!product.getTeamPlayer().getId().equals(productStoreRequestDto.getTeamPlayerId())) {
            TeamPlayer teamPlayer = teamPlayerRepository.findById(productStoreRequestDto.getTeamPlayerId()).orElseThrow(EntityNotFoundException::new);
            product.setTeamPlayer(teamPlayer);
        }

        MultipartFile detailImageFile = productStoreRequestDto.getDetailImageFile();
        if (detailImageFile != null && !detailImageFile.isEmpty()) {
            awsS3FileStore.deleteFile(product.getDetailImageUrl(), ContentType.PRODUCT_DETAIL_IMAGE);
            String iconUrl = awsS3FileStore.uploadFile(detailImageFile, ContentType.PRODUCT_DETAIL_IMAGE);
            product.setDetailImageUrl(iconUrl);
        }

        product.updateByDto(productStoreRequestDto);

        kafkaProductProducer.sendProduct(new ProductKafkaDto(product, KafkaEvent.UPDATE));

        return ProductResponseDto.of(product);
    }

    @Transactional
    public ProductResponseDto deleteProduct(final Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        awsS3FileStore.deleteFile(product.getNftImageUrl(), ContentType.PRODUCT_NFT_IMAGE);
        awsS3FileStore.deleteFile(product.getDetailImageUrl(), ContentType.PRODUCT_DETAIL_IMAGE);
        productRepository.delete(product);
        kafkaProductProducer.sendProduct(new ProductKafkaDto(product, KafkaEvent.DELETE));

        return ProductResponseDto.of(product);
    }
}
