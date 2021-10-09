package com.virspit.virspitproduct.domain.product.feign.wallet;

import lombok.Getter;

@Getter
public class TransactionReceipt {
    private String contractAddress;
    private TransactionStatus status;

    public enum TransactionStatus {
        Submitted, Pending, Committed, CommitError
    }
}
