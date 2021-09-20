package com.virspit.virspitorder.dto.response;

import com.virspit.virspitorder.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponseDto {
    private Long id;
    private Long memberId;
    private Long productId;
    private LocalDateTime orderDate;

    public static OrdersResponseDto entityToDto(Orders orders) {
        return OrdersResponseDto.builder()
                .id(orders.getId())
                .memberId(orders.getMemberId())
                .productId(orders.getProductId())
                .orderDate(orders.getOrderDate())
                .build();
    }
}
