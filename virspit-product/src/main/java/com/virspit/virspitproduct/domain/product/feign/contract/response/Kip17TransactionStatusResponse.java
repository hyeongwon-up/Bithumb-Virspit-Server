package com.virspit.virspitproduct.domain.product.feign.contract.response;

import com.virspit.virspitproduct.domain.product.feign.contract.request.Kip17FeePayerOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Kip17TransactionStatusResponse {
    private String status;
    private String transactionHash;
    private Kip17FeePayerOption options;
}
