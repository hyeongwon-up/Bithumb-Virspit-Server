package com.virspit.virspitservice.domain.advertisement.repository;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AdvertisementDocRepository extends ReactiveMongoRepository<AdvertisementDoc, String> {
    Flux<AdvertisementDoc> findAll();

    @Query("{ id: { $exists: true }}")
    Flux<AdvertisementDoc> findAll(Pageable pageable);
}
