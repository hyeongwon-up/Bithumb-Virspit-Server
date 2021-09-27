package com.virspit.virspitproduct.domain.product.feign.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DeployKip17ContractRequest {
    private String alias;
    private String symbol;
    private String name;
    private Kip17FeePayerOption options;
}
