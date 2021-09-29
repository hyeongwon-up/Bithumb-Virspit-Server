package com.virspit.virspitproduct.domain.sports.controller;

import com.virspit.virspitproduct.domain.common.SuccessResponse;
import com.virspit.virspitproduct.domain.sports.dto.request.SportsStoreRequestDto;
import com.virspit.virspitproduct.domain.sports.dto.response.SportsResponseDto;
import com.virspit.virspitproduct.domain.sports.service.SportsService;
import com.virspit.virspitproduct.error.ErrorResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Api("종목 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sports")
public class SportsController {
    private final SportsService sportsService;

    @ApiOperation(value = "전체 종목 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
    })
    @GetMapping
    public SuccessResponse<List<SportsResponseDto>> getAllSports(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("pageable:{}", pageable);
        return SuccessResponse.of(sportsService.getAllSports(pageable));
    }

    @ApiOperation("ID로 종목 조회")
    @ApiImplicitParam(name = "sportsId", value = "종목 ID", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "존재하지 않는 ID", response = ErrorResponse.class)
    })
    @GetMapping("/{sportsId}")
    public SuccessResponse<SportsResponseDto> findSportsById(@PathVariable Long sportsId) {
        return SuccessResponse.of(sportsService.findSportsById(sportsId));
    }

    @ApiOperation("종목 추가")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "종목 이름(1자 이상 10자 이하)", required = true),
            @ApiImplicitParam(name = "iconFile", value = "아이콘 이미지 파일", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "success"),
            @ApiResponse(code = 400, message = "1.종목 이름, 아이콘 이미지 파일 누락\n2.종목 이름 중복\n3.유효하지 않은 종목 이름", response = ErrorResponse.class)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<SportsResponseDto> addSports(@Valid @ModelAttribute SportsStoreRequestDto sportsCreateRequestDto) throws IOException {
        return SuccessResponse.of(sportsService.createSports(sportsCreateRequestDto), HttpStatus.CREATED);
    }

    @ApiOperation("종목 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sportsId", value = "종목 ID", required = true),
            @ApiImplicitParam(name = "name", value = "종목 이름(1자 이상 10자 이하)", required = true),
            @ApiImplicitParam(name = "iconFile", value = "아이콘 이미지 파일")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "1.종목 이름, ID 누락\n2.종목 이름 중복\n3.유효하지 않은 종목 이름", response = ErrorResponse.class)
    })
    @PutMapping(value = "/{sportsId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SportsResponseDto updateSports(@PathVariable Long sportsId, @Valid @ModelAttribute SportsStoreRequestDto sportsStoreRequestDto) throws IOException {
        return sportsService.updateSports(sportsId, sportsStoreRequestDto);
    }

    @ApiOperation("종목 삭제")
    @ApiImplicitParam(name = "sportsId", value = "종목 ID", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "1.존재하지 않는 종목 ID\n2.ID 타입 불일치", response = ErrorResponse.class)
    })
    @DeleteMapping("/{sportsId}")
    public SuccessResponse<String> deleteSports(@PathVariable Long sportsId) {
        sportsService.deleteSports(sportsId);
        return SuccessResponse.of("deleted");
    }
}
