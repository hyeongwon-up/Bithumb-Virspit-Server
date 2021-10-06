package com.virspit.virspitproduct.domain.sports.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class SportsStoreRequestDto {
    @NotBlank
    @Length(min = 1, max = 20)
    private String name;
    private MultipartFile iconFile;
}
