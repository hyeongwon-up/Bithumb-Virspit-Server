package com.virspit.virspitorder.feign.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class KasWalletKlayTransactionResponse {
    private String feePayer;
    private String from;
    private Integer gas;
    private String gasPrice;
    private String input;
    private Integer nonce;
    private String rlp;
    private String status;
    private String to;
    private String transactionHash;
    private Integer typeInt;
    private String value;
    private Integer feeRatio;
    private String transactionId;
    private String accountKey;
    private Signatures signatures;

    @ToString
    @Getter
    @NoArgsConstructor
    class Signatures{
        private String R;
        private String S;
        private String V;
    }
}
