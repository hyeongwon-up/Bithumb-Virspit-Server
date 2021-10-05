package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.advertisement.common.PageSupport;
import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.dto.ProductKafkaDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductDocRepository productRepository;

    @Transactional
    public Mono<ProductDto> insert(ProductKafkaDto productDto) {
        log.info("mongo db insert :{}", productDto);

        Mono<ProductDto> result = productRepository.save(ProductDoc.kafkaToEntity(productDto))
                .map(ProductDto::entityToDto);
        result.subscribe(p -> log.info("result {}", p));
        return result;
    }

    @Transactional(readOnly = true)
    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .map(ProductDto::entityToDto);
    }

    @Transactional(readOnly = true)
    public Mono<PageSupport> getAllProducts(Pageable pageable) {
        return productRepository.findAllByOrderByCreatedDateDesc()
                .collectList()
                .map(list -> new PageSupport<>(
                        list
                                .stream()
                                .map(p -> ProductDto.entityToDto((ProductDoc) p))
                                .skip(pageable.getPageNumber() * pageable.getPageSize())
                                .limit(pageable.getPageSize())
                                .collect(Collectors.toList()),
                        pageable.getPageNumber(), pageable.getPageSize(), list.size()));
    }

    @Transactional(readOnly = true)
    public Mono<ProductDto> getProduct(final String id) {
        return productRepository.findById(id)
                .map(ProductDto::entityToDto);
    }

    @Transactional(readOnly = true)
    public Flux<ProductDto> getProductsInPriceRange(final int minPrice, final int maxPrice) {
        return productRepository.findByPriceBetween(Range.closed(minPrice, maxPrice))
                .map(ProductDto::entityToDto);
    }

    public Flux<ProductDto> getProductsBy(String search) {
        return productRepository.findByTitleLikeOrderByCreatedDateDesc(search)
                .map(ProductDto::entityToDto);
    }

    @Transactional
    public Mono<Void> deleteProduct(final String id) {
        return productRepository.deleteById(id);
    }
}
