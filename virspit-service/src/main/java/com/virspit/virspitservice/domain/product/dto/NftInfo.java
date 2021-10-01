package com.virspit.virspitservice.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NftInfo {

    private String contractAlias;

    private String metadataUri;
}
