package com.virspit.virspitorder.dto.response;

import com.virspit.virspitorder.entity.Orders;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class OrdersResponseDto {
    private Long id;
    private Long memberId;
    private Long productId;

    public static OrdersResponseDto entityToDto(Orders orders) {
        return OrdersResponseDto.builder()
                .id(orders.getId())
                .memberId(orders.getMemberId())
                .productId(orders.getProductId())
                .build();
    }
}
