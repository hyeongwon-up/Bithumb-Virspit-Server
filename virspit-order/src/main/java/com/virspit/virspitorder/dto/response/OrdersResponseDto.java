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

    @ApiModelProperty("주문한 멤버 ")
    private MemberResponseDto member;

    @ApiModelProperty("주문한 상품 ")
    private ProductResponseDto product;

    @ApiModelProperty("주문한 날짜")
    private LocalDateTime orderDate;

    @ApiModelProperty("메모")
    private String memo;

    public static OrdersResponseDto entityToDto(Orders orders, ProductResponseDto productResponseDto, MemberResponseDto member) {
        return OrdersResponseDto.builder()
                .id(orders.getId())
                .member(member)
                .product(productResponseDto)
                .orderDate(orders.getOrderDate())
                .memo(orders.getMemo())
                .build();
    }
}
