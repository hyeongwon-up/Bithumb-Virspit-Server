package com.virspit.virspitproduct.domain.teamplayer.repository;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.sports.repository.SportsRepository;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamPlayerRepositoryTest {

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private SportsRepository sportsRepository;

    private Sports sports;

    @BeforeEach
    void setup(){
        sports = sportsRepository.save(new Sports("농구", ""));
    }

    @Test
    @DisplayName("TeamPlayer 추가 테스트")
    void createTeamPlayerTest() {
        // given
        String name = "tester";
        int revenueShareRate = 30;
        String description = "this is tester";
        TeamPlayerType teamPlayerType = TeamPlayerType.TEAM;

        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name(name)
                .revenueShareRate(revenueShareRate)
                .description(description)
                .type(teamPlayerType)
                .sports(sports)
                .build();

        // when
        TeamPlayer storedTeamPlayer = teamPlayerRepository.save(teamPlayer);

        // then
        assertThat(storedTeamPlayer.getId()).isNotNull();
        assertThat(storedTeamPlayer.getName()).isEqualTo(name);
        assertThat(storedTeamPlayer.getDescription()).isEqualTo(description);
        assertThat(storedTeamPlayer.getRevenueShareRate()).isEqualTo(revenueShareRate);
        assertThat(storedTeamPlayer.getType()).isEqualTo(teamPlayerType);
        assertThat(storedTeamPlayer.getSports().getId()).isEqualTo(sports.getId());
    }

    @Test
    @DisplayName("전체 TeamPlayer 조회 테스트")
    void findAllTest(){
        // given
        int prevTeamPlayerCount = teamPlayerRepository.findAll().size();
        for (int i = 0; i < 10; i++) {
            saveAndGetTeamPlayer();
        }

        // when
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findAll();

        // then
        assertThat(teamPlayers.size()).isGreaterThan(prevTeamPlayerCount);
    }

    @Test
    @DisplayName("id로 TeamPlayer 조회 테스트")
    void findByIdTest(){
        // given
        TeamPlayer teamPlayer = saveAndGetTeamPlayer();

        // when
        TeamPlayer storedTeamPlayer = teamPlayerRepository.findById(teamPlayer.getId()).get();

        // then
        assertThat(storedTeamPlayer).isEqualTo(teamPlayer);
    }

    @Test
    @DisplayName("id로 TeamPlayer 삭제 테스트")
    void deleteTest(){
        // given
        TeamPlayer teamPlayer = saveAndGetTeamPlayer();

        // when
        teamPlayerRepository.deleteById(teamPlayer.getId());

        // then
        assertThat(teamPlayerRepository.findById(teamPlayer.getId())).isEmpty();
    }

    @Test
    @DisplayName("종목 아이디가 일치하고 이름이 포함된 TeamPlayer 검색 테스트")
    void findByNameContainsAndSportsIdTest() {
        // given
        for (int i = 1; i <= 10; i++) {
            saveAndGetTeamPlayer();
        }

        // when
        List<TeamPlayer> storedTeamPlayers = teamPlayerRepository.findBySportsIdAndName(sports.getId(), "ster");

        // then
        assertThat(storedTeamPlayers.size()).isEqualTo(10);
        storedTeamPlayers.forEach(teamPlayer -> {
            assertThat(teamPlayer.getId()).isNotNull();
            assertThat(teamPlayer.getName()).contains("tester");
            assertThat(teamPlayer.getSports().getId()).isEqualTo(sports.getId());
        });
    }

    TeamPlayer saveAndGetTeamPlayer(){
        String name = "tester";
        int revenueShareRate = 30;
        String description = "this is tester";
        TeamPlayerType teamPlayerType = TeamPlayerType.TEAM;

        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name(name)
                .revenueShareRate(revenueShareRate)
                .description(description)
                .type(teamPlayerType)
                .sports(sports)
                .build();

        return teamPlayerRepository.save(teamPlayer);
    }
}