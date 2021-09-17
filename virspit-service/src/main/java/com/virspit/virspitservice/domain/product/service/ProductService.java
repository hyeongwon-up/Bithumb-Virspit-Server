package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.product.dto.ProductRequestDto;
import com.virspit.virspitservice.domain.product.dto.ProductResponseDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductDocRepository productRepository;

    // 1. 삽입
    @Transactional
    public Mono<ProductResponseDto> insert(Mono<ProductRequestDto> productRequestDto) {
        return productRequestDto.map(ProductDoc::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(ProductResponseDto::entityToDto);
//        return productRepository.save(ProductDoc.dtoToEntity(productRequestDto));
    }

    // 2. 전체 리스트
    public Flux<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .map(ProductResponseDto::entityToDto);
    }

    public Mono<ProductResponseDto> getProduct(final String id) {
        return productRepository.findById(id)
                .map(ProductResponseDto::entityToDto);
    }

    public Flux<ProductResponseDto> getProductsInPriceRange(final int minPrice, final int maxPrice) {
        return productRepository.findByPriceBetween(Range.closed(minPrice, maxPrice));
    }

    // 3. product 검색 리스트
    public Flux<ProductResponseDto> getProductsBy(String search) {

        return null;
    }

    @Transactional
    public Mono<ProductResponseDto> updateProduct(Mono<ProductRequestDto> productRequestDto, String id) {
        return productRepository.findById(id)
                .flatMap(p -> productRequestDto.map(ProductDoc::dtoToEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(productRepository::save)
                .map(ProductResponseDto::entityToDto);
    }

    @Transactional
    public Mono<Void> deleteProduct(final String id) {
        return productRepository.deleteById(id);
    }
}
