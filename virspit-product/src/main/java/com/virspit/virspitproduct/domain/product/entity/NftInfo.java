package com.virspit.virspitproduct.domain.product.entity;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class NftInfo {
    @NotNull
    @Column(nullable = false)
    private String contractAlias;

    @NotNull
    @URL
    @Column(nullable = false)
    private String metadataUri;

    @Builder
    public NftInfo(String contractAlias, String metadataUri) {
        this.contractAlias = contractAlias;
        this.metadataUri = metadataUri;
    }
}
