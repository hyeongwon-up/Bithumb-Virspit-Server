package com.virspit.virspitproduct.domain.teamplayer.service;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.sports.repository.SportsRepository;
import com.virspit.virspitproduct.domain.teamplayer.dto.request.TeamPlayerStoreRequestDto;
import com.virspit.virspitproduct.domain.teamplayer.dto.response.TeamPlayerResponseDto;
import com.virspit.virspitproduct.domain.teamplayer.entity.TeamPlayer;
import com.virspit.virspitproduct.domain.teamplayer.exception.TeamPlayerNotFoundException;
import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepository;
import com.virspit.virspitproduct.domain.teamplayer.repository.TeamPlayerRepositorySupport;
import com.virspit.virspitproduct.error.ErrorCode;
import com.virspit.virspitproduct.error.exception.BusinessException;
import com.virspit.virspitproduct.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamPlayerService {
    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamPlayerRepositorySupport teamPlayerRepositorySupport;
    private final SportsRepository sportsRepository;

    public List<TeamPlayerResponseDto> getTeamPlayers(String name, Long sportsId, final Pageable pageable) {
        return TeamPlayerResponseDto.of(teamPlayerRepositorySupport.findAll(name, sportsId, pageable));
    }

    public TeamPlayerResponseDto getTeamPlayerById(final Long teamPlayerId) {
        return TeamPlayerResponseDto.of(teamPlayerRepository.findById(teamPlayerId).orElseThrow());
    }

    @Transactional
    public TeamPlayerResponseDto createTeamPlayer(final TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        final Long sportsId = teamPlayerStoreRequestDto.getSportsId();
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[ID:%d] 종목", sportsId)));
        TeamPlayer teamPlayer = teamPlayerStoreRequestDto.toTeamPlayer(sports);
        return TeamPlayerResponseDto.of(teamPlayerRepository.save(teamPlayer));
    }

    @Transactional
    public TeamPlayerResponseDto updateTeamPlayer(final Long teamPlayerId, final TeamPlayerStoreRequestDto teamPlayerStoreRequestDto) {
        TeamPlayer teamPlayer = teamPlayerRepository.findById(teamPlayerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[ID:%d] 팀플레이어", teamPlayerId)));

        Long sportsId = teamPlayerStoreRequestDto.getSportsId();
        if (!teamPlayer.getSports().getId().equals(sportsId)) {
            Sports sports = sportsRepository.findById(sportsId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("[ID:%d] 종목", sportsId)));
            teamPlayer.setSports(sports);
        }

        teamPlayer.updateByDto(teamPlayerStoreRequestDto);

        return TeamPlayerResponseDto.of(teamPlayer);
    }

    @Transactional
    public TeamPlayerResponseDto deleteTeamPlayer(final Long teamPlayerId) {
        TeamPlayer teamPlayer = teamPlayerRepository.findById(teamPlayerId)
                .orElseThrow(() -> new TeamPlayerNotFoundException(teamPlayerId));

        if (!teamPlayer.getProducts().isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_EXIST);
        }

        teamPlayerRepository.deleteById(teamPlayerId);

        return TeamPlayerResponseDto.of(teamPlayer);
    }
}
