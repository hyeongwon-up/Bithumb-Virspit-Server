package com.virspit.virspitproduct.domain.sports.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.virspit.virspitproduct.domain.sports.entity.QSports.sports;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SportsRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<Sports> findAll(String name, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (name != null && !name.isBlank()) {
            builder.and(sports.name.contains(name));
        }

        return queryFactory.selectFrom(sports)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(sports.id.desc())
                .fetch();
    }
}
