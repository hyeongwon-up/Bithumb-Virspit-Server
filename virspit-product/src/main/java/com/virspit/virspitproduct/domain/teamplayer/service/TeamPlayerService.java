package com.virspit.virspitproduct.domain.teamplayer.service;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.sports.repository.SportsRepository;
import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TeamPlayerService {
    private final TeamPlayerRepository teamPlayerRepository;
    private final SportsRepository sportsRepository;

    public List<TeamPlayerResponseDto> getAllTeamPlayer() {
        return TeamPlayerResponseDto.of(teamPlayerRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    public TeamPlayerResponseDto getTeamPlayerById(final Long teamPlayerId) {
        return TeamPlayerResponseDto.of(teamPlayerRepository.findById(teamPlayerId).orElseThrow(EntityNotFoundException::new));
    }

    public List<TeamPlayerResponseDto> searchByName(final Long sportsId, final String name) {
        return TeamPlayerResponseDto.of(teamPlayerRepository.findBySportsIdAndName(sportsId, name));
    }

    @Transactional
    public TeamPlayerResponseDto createTeamPlayer(final TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        Sports sports = sportsRepository.findById(teamPlayerStoreRequestDto.getSportsId()).orElseThrow(EntityNotFoundException::new);
        TeamPlayer teamPlayer = teamPlayerStoreRequestDto.toTeamPlayer(sports);
        return TeamPlayerResponseDto.of(teamPlayerRepository.save(teamPlayer));
    }

    @Transactional
    public TeamPlayerResponseDto updateTeamPlayer(final Long teamPlayerId, final TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        TeamPlayer teamPlayer = teamPlayerRepository.findById(teamPlayerId).orElseThrow(EntityNotFoundException::new);

        Long sportsId = teamPlayerStoreRequestDto.getSportsId();
        if (!teamPlayer.getSports().getId().equals(sportsId)) {
            Sports sports = sportsRepository.findById(sportsId).orElseThrow(EntityNotFoundException::new);
            teamPlayer.setSports(sports);
        }

        teamPlayer.setName(teamPlayerStoreRequestDto.getName());
        teamPlayer.setDescription(teamPlayerStoreRequestDto.getDescription());
        teamPlayer.setType(teamPlayerStoreRequestDto.getType());
        teamPlayer.setRevenueShareRate(teamPlayerStoreRequestDto.getRevenueShareRate());

        return TeamPlayerResponseDto.of(teamPlayer);
    }

    @Transactional
    public void deleteTeamPlayer(final Long teamPlayerId) {
        teamPlayerRepository.deleteById(teamPlayerId);
    }
}
