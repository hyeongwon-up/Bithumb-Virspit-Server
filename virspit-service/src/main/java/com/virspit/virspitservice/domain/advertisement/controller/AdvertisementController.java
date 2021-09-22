package com.virspit.virspitservice.domain.advertisement.controller;

import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/advertisement")
@RequiredArgsConstructor
@RestController
public class AdvertisementController {

    @PostMapping
    public void insert(@RequestBody AdvertisementRequestDto advertisement){

    }
}
