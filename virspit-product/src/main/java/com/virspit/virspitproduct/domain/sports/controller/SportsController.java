package com.virspit.virspitproduct.domain.sports.controller;

import com.virspit.virspitproduct.domain.sports.dto.request.SportsStoreRequestDto;
import com.virspit.virspitproduct.domain.sports.dto.response.SportsResponseDto;
import com.virspit.virspitproduct.domain.sports.service.SportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sports")
public class SportsController {
    private final SportsService sportsService;

    @GetMapping
    public List<SportsResponseDto> getAllSports() {
        return sportsService.getAllSports();
    }

    @GetMapping("/{sportsId}")
    public SportsResponseDto findSportsById(@PathVariable Long sportsId) {
        return sportsService.findSportsById(sportsId);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public SportsResponseDto addSports(@Valid @ModelAttribute SportsStoreRequestDto sportsCreateRequestDto) throws IOException {
        return sportsService.addSports(sportsCreateRequestDto);
    }

    @PutMapping("/{sportsId}")
    public SportsResponseDto updateSports(@PathVariable Long sportsId, @Valid @ModelAttribute SportsStoreRequestDto sportsCreateRequestDto) throws IOException {
        return sportsService.updateSports(sportsId, sportsCreateRequestDto);
    }

    @DeleteMapping("/{sportsId}")
    public void deleteSports(@PathVariable Long sportsId) {
        sportsService.deleteSports(sportsId);
    }
}
