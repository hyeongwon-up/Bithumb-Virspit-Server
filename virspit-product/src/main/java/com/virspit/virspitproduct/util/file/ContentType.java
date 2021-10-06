package com.virspit.virspitproduct.util.file;

import lombok.Getter;

public enum ContentType {
    SPORTS_ICON_IMAGE("images/sports/icon/", Type.ALL_IMAGES),
    PRODUCT_DETAIL_IMAGE("images/product/detail/", Type.ALL_IMAGES),
    PRODUCT_NFT_IMAGE("images/product/nft/", Type.ALL_IMAGES);

    @Getter
    private final String path;
    @Getter
    private final String type;

    ContentType(final String path, final String type) {
        this.path = path;
        this.type = type;
    }

    private static class Type {
        private static final String ALL_IMAGES = "image/";
    }
}
