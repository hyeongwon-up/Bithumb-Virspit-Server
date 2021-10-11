package com.virspit.virspitproduct.domain.product.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class OrderDto {
    private Long id;
    private OrderProduct product;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @ToString
    static class OrderProduct {
        private Long id;
    }
}
