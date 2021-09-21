package com.virspit.virspitproduct.domain.teamplayer.service;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.sports.repository.SportsRepository;
import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TeamPlayerServiceTest {

    @InjectMocks
    private TeamPlayerService teamPlayerService;

    @Mock
    private TeamPlayerRepository teamPlayerRepository;

    @Mock
    private SportsRepository sportsRepository;

    @Test
    @DisplayName("모든 TeamPlayer 조회 테스트")
    void getAllTeamPlayerTest() {
        // given
        List<TeamPlayer> teamPlayers = new ArrayList<>();
        for (long i = 1; i < 11; i++) {
            TeamPlayer teamPlayer = TeamPlayer.builder()
                    .name("tester team")
                    .description("this is tester team")
                    .revenueShareRate(20)
                    .type(TeamPlayerType.TEAM)
                    .sports(new Sports("test", ""))
                    .build();
            ReflectionTestUtils.setField(teamPlayer, "id", i);
            teamPlayers.add(teamPlayer);
        }

        given(teamPlayerRepository.findAll(any(Sort.class))).willReturn(teamPlayers);

        // when
        List<TeamPlayerResponseDto> teamPlayerResponseDtos = teamPlayerService.getAllTeamPlayer();

        // then
        assertThat(teamPlayerResponseDtos.size()).isEqualTo(teamPlayers.size());
        for (int i = 0; i < teamPlayerResponseDtos.size(); i++) {
            TeamPlayerResponseDto teamPlayerResponseDto = teamPlayerResponseDtos.get(i);
            TeamPlayer teamPlayer = teamPlayers.get(i);

            assertThat(teamPlayerResponseDto.getId()).isEqualTo(teamPlayer.getId());
            assertThat(teamPlayerResponseDto.getName()).isEqualTo(teamPlayer.getName());
            assertThat(teamPlayerResponseDto.getDescription()).isEqualTo(teamPlayer.getDescription());
            assertThat(teamPlayerResponseDto.getSportsId()).isEqualTo(teamPlayer.getSports().getId());
            assertThat(teamPlayerResponseDto.getType()).isEqualTo(teamPlayer.getType());
            assertThat(teamPlayerResponseDto.getRevenueShareRate()).isEqualTo(teamPlayer.getRevenueShareRate());
        }
    }

    @Test
    @DisplayName("id로 TeamPlayer 조회 테스트")
    void getTeamPlayerById() {
        // given
        long testId = 10L;
        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name("tester team")
                .description("this is tester team")
                .revenueShareRate(20)
                .type(TeamPlayerType.TEAM)
                .sports(new Sports("test", ""))
                .build();

        ReflectionTestUtils.setField(teamPlayer, "id", testId);

        // when
        given(teamPlayerRepository.findById(testId)).willReturn(Optional.of(teamPlayer));

        // then
        TeamPlayerResponseDto teamPlayerResponseDto = teamPlayerService.getTeamPlayerById(testId);

        assertThat(teamPlayerResponseDto.getId()).isEqualTo(teamPlayer.getId());
        assertThat(teamPlayerResponseDto.getName()).isEqualTo(teamPlayer.getName());
        assertThat(teamPlayerResponseDto.getDescription()).isEqualTo(teamPlayer.getDescription());
        assertThat(teamPlayerResponseDto.getSportsId()).isEqualTo(teamPlayer.getSports().getId());
        assertThat(teamPlayerResponseDto.getType()).isEqualTo(teamPlayer.getType());
        assertThat(teamPlayerResponseDto.getRevenueShareRate()).isEqualTo(teamPlayer.getRevenueShareRate());
    }

    @Test
    @DisplayName("sports id가 같고 이름이 포함된 TeamPlayer 조회 테스트")
    void searchByName() {
        // given
        long sportsId = 10L;
        String searchWord = "est";

        List<TeamPlayer> teamPlayers = new ArrayList<>();
        for (long i = 1; i < 11; i++) {
            TeamPlayer teamPlayer = TeamPlayer.builder()
                    .name("tester team")
                    .description("this is tester team")
                    .revenueShareRate(20)
                    .type(TeamPlayerType.TEAM)
                    .sports(new Sports("test", ""))
                    .build();

            ReflectionTestUtils.setField(teamPlayer, "id", i);
            ReflectionTestUtils.setField(teamPlayer.getSports(), "id", sportsId);

            teamPlayers.add(teamPlayer);
        }

        given(teamPlayerRepository.findBySportsIdAndName(sportsId, searchWord)).willReturn(teamPlayers);

        // when
        List<TeamPlayerResponseDto> teamPlayerResponseDtos = teamPlayerService.searchByName(sportsId, searchWord);

        // then
        assertThat(teamPlayerResponseDtos.size()).isEqualTo(teamPlayers.size());
        teamPlayerResponseDtos.forEach(teamPlayerResponseDto -> {
            assertThat(teamPlayerResponseDto.getName()).contains(searchWord);
            assertThat(teamPlayerResponseDto.getSportsId()).isEqualTo(sportsId);
        });
    }

    @Test
    @DisplayName("TeamPlayer 생성 테스트")
    void createTeamPlayer() {
        // given
        long sportsId = 20L;
        long teamPlayerId = 10L;
        String name = "tester team";
        String description = "this is tester team";
        int revenueShareRate = 20;
        TeamPlayerType teamPlayerType = TeamPlayerType.TEAM;

        Sports sports = new Sports("test", "");
        ReflectionTestUtils.setField(sports, "id", sportsId);

        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name(name)
                .description(description)
                .revenueShareRate(revenueShareRate)
                .type(teamPlayerType)
                .sports(sports)
                .build();
        ReflectionTestUtils.setField(teamPlayer, "id", teamPlayerId);

        TeamPlayerStoreRequestDto teamPlayerStoreRequestDto = TeamPlayerStoreRequestDto.builder()
                .name(name)
                .description(description)
                .type(teamPlayerType)
                .revenueShareRate(revenueShareRate)
                .sportsId(sportsId)
                .build();

        given(sportsRepository.findById(sportsId)).willReturn(Optional.of(sports));
        given(teamPlayerRepository.save(any())).willReturn(teamPlayer);

        // when
        TeamPlayerResponseDto teamPlayerResponseDto = teamPlayerService.createTeamPlayer(teamPlayerStoreRequestDto);

        // then
        assertThat(teamPlayerResponseDto.getId()).isEqualTo(teamPlayerId);
        assertThat(teamPlayerResponseDto.getName()).isEqualTo(name);
        assertThat(teamPlayerResponseDto.getDescription()).isEqualTo(description);
        assertThat(teamPlayerResponseDto.getSportsId()).isEqualTo(sportsId);
        assertThat(teamPlayerResponseDto.getType()).isEqualTo(teamPlayerType);
        assertThat(teamPlayerResponseDto.getRevenueShareRate()).isEqualTo(revenueShareRate);
    }

    @Test
    @DisplayName("TeamPlayer 수정 테스트")
    void updateTeamPlayer() {
        // given
        long teamPlayerId = 10L;

        long originSportsId = 20L;
        long updatedSportsId = 1L;

        String name = "tester team";
        String updatedName = "update tester";

        String description = "this is tester team";
        String updatedDescription = "this is update tester";

        int revenueShareRate = 20;
        int updatedRevenueShareRate = 70;

        TeamPlayerType teamPlayerType = TeamPlayerType.TEAM;
        TeamPlayerType updatedTeamPlayerType = TeamPlayerType.PLAYER;

        Sports originSports = new Sports("football", "");
        ReflectionTestUtils.setField(originSports, "id", originSportsId);
        Sports updatedSports = new Sports("baseball", "https://icon/baseball");
        ReflectionTestUtils.setField(updatedSports, "id", updatedSportsId);

        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name("tester team")
                .description("this is tester")
                .revenueShareRate(20)
                .type(TeamPlayerType.TEAM)
                .sports(originSports)
                .build();
        ReflectionTestUtils.setField(teamPlayer, "id", teamPlayerId);

        TeamPlayerStoreRequestDto teamPlayerStoreRequestDto = TeamPlayerStoreRequestDto.builder()
                .name(updatedName)
                .description(updatedDescription)
                .type(updatedTeamPlayerType)
                .revenueShareRate(updatedRevenueShareRate)
                .sportsId(updatedSportsId)
                .build();

        given(sportsRepository.findById(updatedSportsId)).willReturn(Optional.of(updatedSports));
        given(teamPlayerRepository.findById(teamPlayerId)).willReturn(Optional.of(teamPlayer));

        // when
        TeamPlayerResponseDto teamPlayerResponseDto = teamPlayerService.updateTeamPlayer(teamPlayerId, teamPlayerStoreRequestDto);

        // then
        assertThat(teamPlayerResponseDto.getId()).isEqualTo(teamPlayerId);
        assertThat(teamPlayerResponseDto.getName()).isEqualTo(updatedName);
        assertThat(teamPlayerResponseDto.getDescription()).isEqualTo(updatedDescription);
        assertThat(teamPlayerResponseDto.getSportsId()).isEqualTo(updatedSportsId);
        assertThat(teamPlayerResponseDto.getType()).isEqualTo(updatedTeamPlayerType);
        assertThat(teamPlayerResponseDto.getRevenueShareRate()).isEqualTo(updatedRevenueShareRate);
    }
}