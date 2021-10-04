package com.virspit.virspitproduct.domain.product.exception;

import com.virspit.virspitproduct.error.exception.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(final Long sportsId) {
        super(String.format("ID가 %d인 상품", sportsId));
    }
}
