package com.virspit.virspitproduct.domain.sports.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SportsStoreRequestDto {
    @NotBlank
    @Length(min = 1, max = 10)
    private String name;
    private MultipartFile iconFile;
}
