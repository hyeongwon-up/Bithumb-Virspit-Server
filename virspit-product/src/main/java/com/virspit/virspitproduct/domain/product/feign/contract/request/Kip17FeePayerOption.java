package com.virspit.virspitproduct.domain.product.feign.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Kip17FeePayerOption {
    private Boolean enableGlobalFeePayer;
    private UserFeePayer userFeePayer;
}
