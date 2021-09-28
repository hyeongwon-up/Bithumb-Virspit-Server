package com.virspit.virspitproduct.domain.sports.service;

import com.virspit.virspitproduct.domain.sports.dto.request.SportsStoreRequestDto;
import com.virspit.virspitproduct.domain.sports.dto.response.SportsResponseDto;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.sports.exception.IconFileNotFoundException;
import com.virspit.virspitproduct.domain.sports.exception.NameDuplicatedException;
import com.virspit.virspitproduct.domain.sports.exception.TeamPlayerExistException;
import com.virspit.virspitproduct.domain.sports.repository.SportsRepository;
import com.virspit.virspitproduct.error.exception.EntityNotFoundException;
import com.virspit.virspitproduct.util.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SportsService {
    private final SportsRepository sportsRepository;
    private final FileStore localFileStore;

    public List<SportsResponseDto> getAllSports(Pageable pageable) {
        return SportsResponseDto.of(sportsRepository.findAll(pageable).toList());
    }

    public SportsResponseDto findSportsById(final Long sportsId) {
        return SportsResponseDto.of(sportsRepository.findById(sportsId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[ID:%d] 종목", sportsId))));
    }

    @Transactional
    public SportsResponseDto createSports(final SportsStoreRequestDto sportsCreateRequestDto) throws IOException {
        String name = sportsCreateRequestDto.getName();
        if (sportsRepository.existsSportsByName(name)) {
            throw new NameDuplicatedException();
        }

        MultipartFile iconFile = sportsCreateRequestDto.getIconFile();
        if (iconFile == null || iconFile.isEmpty()) {
            throw new IconFileNotFoundException();
        }

        String iconFileUrl = localFileStore.uploadFile(iconFile);

        return SportsResponseDto.of(sportsRepository.save(new Sports(name, iconFileUrl)));
    }

    @Transactional
    public SportsResponseDto updateSports(final Long sportsId, final SportsStoreRequestDto sportsStoreRequestDto) throws IOException {
        Sports storedSports = sportsRepository.findById(sportsId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[ID:%d] 종목", sportsId)));

        storedSports.setName(sportsStoreRequestDto.getName());

        MultipartFile iconFile = sportsStoreRequestDto.getIconFile();
        if (iconFile != null && !iconFile.isEmpty()) {
            localFileStore.deleteFile(storedSports.getIconUrl());
            String iconUrl = localFileStore.uploadFile(iconFile);
            storedSports.setIconUrl(iconUrl);
        }

        return SportsResponseDto.of(storedSports);
    }

    @Transactional
    public void deleteSports(final Long sportsId) {
        Sports sports = sportsRepository.findById(sportsId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[ID:%d] 종목", sportsId)));

        if (!sports.getTeamPlayers().isEmpty()) {
            throw new TeamPlayerExistException();
        }

        localFileStore.deleteFile(sports.getIconUrl());
        sportsRepository.deleteById(sportsId);
    }
}
