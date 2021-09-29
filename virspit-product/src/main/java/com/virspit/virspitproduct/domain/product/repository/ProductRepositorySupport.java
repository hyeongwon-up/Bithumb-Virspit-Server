package com.virspit.virspitproduct.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.virspit.virspitproduct.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.virspit.virspitproduct.domain.product.entity.QProduct.product;
import static com.virspit.virspitproduct.domain.teamplayer.entity.QTeamPlayer.teamPlayer;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<Product> findAll(String keyword, Long teamPlayerId, Long sportsId, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            builder.and(product.title.contains(keyword));
        }

        if (teamPlayerId != null) {
            builder.and(product.teamPlayer.id.eq(teamPlayerId));
        } else if (sportsId != null) {
            builder.and(teamPlayer.sports.id.eq(sportsId));
        }

        return queryFactory.selectFrom(product)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
