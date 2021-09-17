package com.virspit.virspitservice.domain.product.repository;

import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public interface ProductDocRepository extends ReactiveMongoRepository<ProductDoc, String> {
    Optional<ProductDoc> findByName(String id);

    Flux<ProductDoc> findAll();

    @Query("{$regex: ?0}")
    Flux<ProductDoc> findAllPagingBy(Pageable page);

    @Query("{name: ?0}")
    Flux<ProductDoc> findByNameLike(String name);

    @Query("{'name':{${regex: ?0}}")
    Flux<ProductDoc> findByNameLikePagingBy(String name, Pageable page);

}
