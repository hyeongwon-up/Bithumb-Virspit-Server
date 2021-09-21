package com.virspit.virspitproduct.domain.sports.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SportsStoreRequestDto {
    @NotBlank
    private String name;
    private MultipartFile iconFile;
}
