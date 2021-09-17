package com.virspit.virspitservice.domain.product.repository;

import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDocRepository extends ReactiveMongoRepository<ProductDoc, String> {
    Mono<ProductDoc> findByName(String id);

    @Query("{ id: { $exists: true }}")
    Flux<ProductDoc> findAllPagingBy(Pageable page);

    @Query("{name:{$regex: ?0}}")
    Flux<ProductDoc> findByNameLikeOrderByCreatedDateDesc(String name);

    @Query("{name:{$regex: ?0}}, id: { $exists: true }}")
    Flux<ProductDoc> findByNameLikePagingBy(String name, Pageable page);

    Flux<ProductDoc> findByPriceBetween(Range<Integer> priceRange);
}
