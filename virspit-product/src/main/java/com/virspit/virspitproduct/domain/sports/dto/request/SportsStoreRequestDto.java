package com.virspit.virspitproduct.domain.sports.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SportsStoreRequestDto {
    private String name;
    private MultipartFile iconFile;
}
