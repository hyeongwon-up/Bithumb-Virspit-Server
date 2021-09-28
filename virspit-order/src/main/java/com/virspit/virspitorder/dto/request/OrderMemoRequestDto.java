package com.virspit.virspitorder.dto.request;

import lombok.*;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderMemoRequestDto {
    private Long orderId;
    private String memo;
}
