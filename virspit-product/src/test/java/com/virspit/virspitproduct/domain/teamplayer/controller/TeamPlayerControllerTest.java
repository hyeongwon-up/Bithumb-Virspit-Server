package com.virspit.virspitproduct.domain.teamplayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayerType;
import com.virspit.virspitproduct.domain.teamplayer.service.TeamPlayerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TeamPlayerController.class)
@MockBean(JpaMetamodelMappingContext.class)
class TeamPlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeamPlayerService teamPlayerService;

    @Test
    @DisplayName("TeamPlayer 생성 테스트")
    void createTeamPlayerTest() throws Exception {
        long sportsId = 10L;
        String name = "tester";
        String description = "this is tester";
        int revenueShareRate = 70;
        TeamPlayerType teamPlayerType = TeamPlayerType.PLAYER;
        TeamPlayerStoreRequestDto teamPlayerStoreRequestDto = TeamPlayerStoreRequestDto.builder()
                .sportsId(sportsId)
                .name(name)
                .description(description)
                .revenueShareRate(revenueShareRate)
                .type(teamPlayerType)
                .build();

        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name(name)
                .description(description)
                .revenueShareRate(revenueShareRate)
                .sports(new Sports("football", ""))
                .type(teamPlayerType)
                .build();

        TeamPlayerResponseDto teamPlayerResponseDto = TeamPlayerResponseDto.of(teamPlayer);

        given(teamPlayerService.createTeamPlayer(any())).willReturn(teamPlayerResponseDto);

        mockMvc.perform(post("/team-player")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamPlayerStoreRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(teamPlayerResponseDto)));
    }

    @Test
    void getAllTeamPlayer() throws Exception {
        String name = "tester";
        String description = "this is tester";
        int revenueShareRate = 70;
        TeamPlayerType teamPlayerType = TeamPlayerType.PLAYER;

        List<TeamPlayerResponseDto> teamPlayerResponseDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TeamPlayer teamPlayer = TeamPlayer.builder()
                    .name(name + i)
                    .description(description)
                    .revenueShareRate(revenueShareRate)
                    .sports(new Sports("football", ""))
                    .type(teamPlayerType)
                    .build();
            teamPlayerResponseDtos.add(TeamPlayerResponseDto.of(teamPlayer));
        }

        given(teamPlayerService.getAllTeamPlayer()).willReturn(teamPlayerResponseDtos);

        mockMvc.perform(get("/team-player").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(teamPlayerResponseDtos)));
    }

    @Test
    @DisplayName("sportsId가 같고 이름이 포함된 TeamPlayer 검색 테스트")
    void searchByName() throws Exception {
        long sportsId = 10L;
        String name = "tester";
        String description = "this is tester";
        int revenueShareRate = 70;
        TeamPlayerType teamPlayerType = TeamPlayerType.PLAYER;

        List<TeamPlayerResponseDto> teamPlayerResponseDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TeamPlayer teamPlayer = TeamPlayer.builder()
                    .name(name + i)
                    .description(description)
                    .revenueShareRate(revenueShareRate)
                    .sports(new Sports("football", ""))
                    .type(teamPlayerType)
                    .build();
            teamPlayerResponseDtos.add(TeamPlayerResponseDto.of(teamPlayer));
        }

        String keywordName = "est1";

        List<TeamPlayerResponseDto> filteredTeamPlayerResponseDtos = teamPlayerResponseDtos.stream()
                .filter(teamPlayerResponseDto -> teamPlayerResponseDto.getName().contains(keywordName))
                .collect(Collectors.toList());

        given(teamPlayerService.searchByName(sportsId, keywordName))
                .willReturn(filteredTeamPlayerResponseDtos);

        mockMvc.perform(get("/team-player/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("sportsId", String.valueOf(sportsId))
                        .param("name", "est"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(filteredTeamPlayerResponseDtos)));
    }

    @Test
    void getTeamPlayerById() throws Exception {
        long teamPlayerId = 10L;
        String name = "tester";
        String description = "this is tester";
        int revenueShareRate = 70;
        TeamPlayerType teamPlayerType = TeamPlayerType.PLAYER;

        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name(name)
                .description(description)
                .revenueShareRate(revenueShareRate)
                .sports(new Sports("football", ""))
                .type(teamPlayerType)
                .build();

        TeamPlayerResponseDto teamPlayerResponseDto = TeamPlayerResponseDto.of(teamPlayer);

        given(teamPlayerService.getTeamPlayerById(teamPlayerId)).willReturn(teamPlayerResponseDto);

        mockMvc.perform(get("/team-player/{teamPlayerId}", teamPlayerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(teamPlayerResponseDto)));
    }

    @Test
    void updateTeamPlayer() throws Exception {
        long teamPlayerId = 10L;
        long sportsId = 20L;
        String name = "tester";
        String description = "this is tester";
        int revenueShareRate = 70;
        TeamPlayerType teamPlayerType = TeamPlayerType.PLAYER;

        TeamPlayer teamPlayer = TeamPlayer.builder()
                .name(name)
                .description(description)
                .revenueShareRate(revenueShareRate)
                .sports(new Sports("football", ""))
                .type(teamPlayerType)
                .build();

        ReflectionTestUtils.setField(teamPlayer, "id", teamPlayerId);
        ReflectionTestUtils.setField(teamPlayer.getSports(), "id", sportsId);

        TeamPlayerStoreRequestDto teamPlayerStoreRequestDto = TeamPlayerStoreRequestDto.builder()
                .sportsId(sportsId)
                .name(name)
                .description(description)
                .revenueShareRate(revenueShareRate)
                .type(teamPlayerType)
                .build();

        TeamPlayerResponseDto teamPlayerResponseDto = TeamPlayerResponseDto.of(teamPlayer);

        given(teamPlayerService.updateTeamPlayer(eq(teamPlayerId), any())).willReturn(teamPlayerResponseDto);

        mockMvc.perform(put("/team-player/{teamPlayerId}", teamPlayerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamPlayerStoreRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(teamPlayerResponseDto)));
    }

    @Test
    void deleteTeamPlayer() throws Exception {
        long teamPlayerId = 10L;

        mockMvc.perform(get("/team-player/{teamPlayerId}", teamPlayerId))
                .andExpect(status().isOk());
    }
}