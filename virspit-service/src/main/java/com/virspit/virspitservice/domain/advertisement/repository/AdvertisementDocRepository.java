package com.virspit.virspitservice.domain.advertisement.repository;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AdvertisementDocRepository extends ReactiveMongoRepository<AdvertisementDoc, String> {

    Flux<ProductDoc> findAll(Pageable pageable);
}
