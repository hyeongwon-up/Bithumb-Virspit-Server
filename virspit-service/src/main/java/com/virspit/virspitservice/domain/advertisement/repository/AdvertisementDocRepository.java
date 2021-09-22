package com.virspit.virspitservice.domain.advertisement.repository;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementDocRepository extends ReactiveMongoRepository<AdvertisementDoc, String> {

}
