package com.virspit.virspitproduct.domain.product.feign.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserFeePayer {
    private String krn;
    private String address;
}
