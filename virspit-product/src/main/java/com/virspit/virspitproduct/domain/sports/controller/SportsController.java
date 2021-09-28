package com.virspit.virspitproduct.domain.sports.controller;

import com.virspit.virspitproduct.domain.common.SuccessResponse;
import com.virspit.virspitproduct.domain.sports.dto.request.SportsStoreRequestDto;
import com.virspit.virspitproduct.domain.sports.dto.response.SportsResponseDto;
import com.virspit.virspitproduct.domain.sports.service.SportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sports")
public class SportsController {
    private final SportsService sportsService;

    @GetMapping
    public SuccessResponse<List<SportsResponseDto>> getAllSports(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return SuccessResponse.of(sportsService.getAllSports(pageable));
    }

    @GetMapping("/{sportsId}")
    public SuccessResponse<SportsResponseDto> findSportsById(@PathVariable Long sportsId) {
        return SuccessResponse.of(sportsService.findSportsById(sportsId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<SportsResponseDto> addSports(@Valid @ModelAttribute SportsStoreRequestDto sportsCreateRequestDto) throws IOException {
        return SuccessResponse.of(sportsService.createSports(sportsCreateRequestDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{sportsId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SportsResponseDto updateSports(@PathVariable Long sportsId, @Valid @ModelAttribute SportsStoreRequestDto sportsStoreRequestDto) throws IOException {
        return sportsService.updateSports(sportsId, sportsStoreRequestDto);
    }

    @DeleteMapping("/{sportsId}")
    public SuccessResponse<String> deleteSports(@PathVariable Long sportsId) {
        sportsService.deleteSports(sportsId);
        return SuccessResponse.of("deleted");
    }
}
