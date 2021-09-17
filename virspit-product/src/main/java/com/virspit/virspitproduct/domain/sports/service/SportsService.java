package com.virspit.virspitproduct.domain.sports.service;

import com.virspit.virspitproduct.domain.sports.dto.request.SportsStoreRequestDto;
import com.virspit.virspitproduct.domain.sports.dto.response.SportsResponseDto;
import com.virspit.virspitproduct.domain.sports.entity.Sports;
import com.virspit.virspitproduct.domain.sports.repository.SportsRepository;
import com.virspit.virspitproduct.util.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SportsService {
    private final SportsRepository sportsRepository;
    private final FileStore fileStore;

    public List<SportsResponseDto> getAllSports() {
        return SportsResponseDto.of(sportsRepository.findAll());
    }

    public SportsResponseDto findSportsById(final Long sportsId) {
        return SportsResponseDto.of(sportsRepository.findById(sportsId).orElseThrow(EntityNotFoundException::new));
    }

    public SportsResponseDto addSports(final SportsStoreRequestDto sportsCreateRequestDto) throws IOException {
        MultipartFile iconFile = sportsCreateRequestDto.getIconFile();
        String filename = fileStore.store(iconFile);

        if (filename == null) {
            log.info("failed to store file");
        }

        return SportsResponseDto.of(sportsRepository.save(new Sports(sportsCreateRequestDto.getName(), filename)));
    }

    public SportsResponseDto updateSports(final Long sportsId, final SportsStoreRequestDto sportsStoreRequestDto) throws IOException {
        Sports storedSports = sportsRepository.findById(sportsId).orElseThrow(EntityNotFoundException::new);
        storedSports.setName(sportsStoreRequestDto.getName());

        MultipartFile iconFile = sportsStoreRequestDto.getIconFile();
        if (!iconFile.isEmpty()) {
            String newFilename = fileStore.store(iconFile);

            if (fileStore.delete(storedSports.getIconUrl())) {
                log.error("failed to delete file. filename: {}", storedSports.getIconUrl());
            }

            if (newFilename != null) {
                storedSports.setIconUrl(newFilename);
            }
        }

        return SportsResponseDto.of(sportsRepository.save(storedSports));
    }

    public void deleteSports(final Long sportsId) {
        Sports sports = sportsRepository.findById(sportsId).orElseThrow(EntityNotFoundException::new);
        fileStore.delete(sports.getIconUrl());
        sportsRepository.deleteById(sportsId);
    }
}
