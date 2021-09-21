package com.virspit.virspitorder.dto.response;

import com.virspit.virspitorder.entity.Orders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel("주문 정보 응답 DTO")
@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponseDto {

    @ApiModelProperty("주문 id")
    private Long id;
    @ApiModelProperty("주문한 멤버 id")
    private Long memberId;
    @ApiModelProperty("주문한 상품 id")
    private Long productId;
    @ApiModelProperty("주문한 날짜")
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
