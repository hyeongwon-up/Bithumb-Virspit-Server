package com.virspit.virspitproduct.util.file;

import lombok.Getter;

public enum ContentType {
    SPORTS_ICON_IMAGE("images/icon/"),
    PRODUCT_DETAIL_IMAGE("images/product/detail/"),
    PRODUCT_NFT_IMAGE("images/product/nft/");

    @Getter
    private final String path;

    ContentType(String path) {
        this.path = path;
    }
}
