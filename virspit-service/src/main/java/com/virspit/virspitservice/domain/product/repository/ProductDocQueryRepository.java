package com.virspit.virspitservice.domain.product.repository;


import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDocQueryRepository extends ReactiveMongoRepository<ProductDoc, String>,
        ReactiveQuerydslPredicateExecutor<ProductDoc> {
}
