package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.product.dto.ProductRequestDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class ProductService {
//    private final ProductDocRepository productRepository;

    // 1. 삽입
    @Transactional
    public void insert(ProductRequestDto productRequestDto) {

    }

    // 2. 전체 리스트
    public Flux<ProductDoc> findAllProducts() {
        return null;
    }

    // 3. product 검색 리스트
    public Flux<ProductDoc> findProductsBy(String search) {
        return null;
    }
}
