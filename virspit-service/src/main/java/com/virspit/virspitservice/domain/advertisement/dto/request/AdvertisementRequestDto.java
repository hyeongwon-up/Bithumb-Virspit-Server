package com.virspit.virspitservice.domain.advertisement.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AdvertisementRequestDto {
    private String description;
    private String productId;
    private String url;

}
