package com.virspit.virspitservice.domain.product.repository;

import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ProductDocRepository extends ReactiveMongoRepository<ProductDoc, String> {
    Mono<ProductDoc> findByTitle(String title);

    @Query("{ id: { $exists: true }}")
    Flux<ProductDoc> findAll(Pageable page);

    @Query("{title:{$regex: ?0}}")
    Flux<ProductDoc> findByTitleLikeOrderByCreatedDateDesc(String title);

    @Query("{title:{$regex: ?0}}, id: { $exists: true }}")
    Flux<ProductDoc> findByTitleLikePagingBy(String name, Pageable page);

    Flux<ProductDoc> findByPriceBetween(Range<Integer> priceRange);

    Flux<ProductDoc> findAllByOrderByCreatedDateTimeDesc();

    Flux<ProductDoc> findByTeamPlayerTypeOrderByCreatedDateTimeDesc(String teamPlayerType);

    Flux<ProductDoc> findBySportsIdAndTeamPlayerTypeOrderByCreatedDateTimeDesc(Long sportsId, String teamPlayerType);

    Flux<ProductDoc> findBySportsIdOrderByCreatedDateTimeDesc(Long sportsId);

}
