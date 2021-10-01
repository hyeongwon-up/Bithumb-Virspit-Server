package com.virspit.virspitproduct.domain.teamplayer.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.virspit.virspitproduct.domain.teamplayer.entity.QTeamPlayer.teamPlayer;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeamPlayerRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<TeamPlayer> findAll(String name, Long sportsId, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (name != null && !name.isBlank()) {
            builder.and(teamPlayer.name.contains(name));
        }

        if (sportsId != null) {
            builder.and(teamPlayer.sports.id.eq(sportsId));
        }

        return queryFactory.selectFrom(teamPlayer)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(teamPlayer.id.desc())
                .fetch();
    }
}
