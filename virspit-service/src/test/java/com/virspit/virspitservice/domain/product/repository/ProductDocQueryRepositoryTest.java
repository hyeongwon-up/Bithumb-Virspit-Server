package com.virspit.virspitservice.domain.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import static com.virspit.virspitservice.domain.product.entity.QProductDoc.productDoc;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class ProductDocQueryRepositoryTest {

    @Autowired
    private ProductDocQueryRepository productDocQueryRepository;

    @Autowired
    private ProductDocRepository productDocRepository;

    @Test
    void getAll(){
        Flux<ProductDoc> result = productDocQueryRepository.findAll(
                productDoc.sportsId.eq(1l),
                new OrderSpecifier<>(Order.DESC, productDoc.createdDateTime))
                .switchIfEmpty(Flux.empty())
                .log();

        System.out.println(result);
    }

    @Test
    void getAllBySports(){
//        Flux<ProductDoc> result =
//                productDocRepository
//                .findBySportsIdAndTeamPlayerTypeOrderByCreatedDateTimeDesc(1l, "PLAYER")
//                .subscribe(s-> System.out.println(s));

                productDocRepository
                        .findByTeamPlayerTypeOrderByCreatedDateTimeDesc("PLAYER")
                        .subscribe(s-> System.out.println(s));
    }
}