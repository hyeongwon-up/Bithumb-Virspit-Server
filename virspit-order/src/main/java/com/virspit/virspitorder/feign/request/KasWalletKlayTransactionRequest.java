package com.virspit.virspitorder.feign.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class KasWalletKlayTransactionRequest {
    private String from;
    private String value;
    private String to;
    private String feePayer;
    private Integer feeRatio;
}
