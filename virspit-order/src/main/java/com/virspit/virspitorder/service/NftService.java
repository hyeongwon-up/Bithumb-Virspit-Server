package com.virspit.virspitorder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TokenListResponse;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TransactionStatusResponse;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.TransactionReceipt;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.TransactionResult;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.ValueTransferTransactionRequest;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class NftService {
    private final static String UNIT = "KLAY";
    private final CaverExtKAS caver;

    @Value("${kas.admin-wallet-address}")
    private String adminWalletAddress;


    public boolean payToAdminFeesByCustomer(int price, String memberWalletAddress) throws ApiException {
        String value = caver.utils.convertToPeb(String.valueOf(price), UNIT);
        BigInteger bi = new BigInteger(value, 10);
        String priceValue = "0x" + bi.toString(16);

        ValueTransferTransactionRequest request = new ValueTransferTransactionRequest();
        request.setTo(adminWalletAddress);
        request.setFrom(memberWalletAddress);
        request.setValue(priceValue);
        request.setSubmit(true);

        TransactionResult transactionResult = caver.kas.wallet.requestValueTransfer(request);

        log.info("transactionResult:: transactionHash {}, transactionStatus {}",
                transactionResult.getTransactionHash(),
                transactionResult.getStatus());

        log.info("klay result : {}", isCommitted(transactionResult.getTransactionHash()));
        return true;
    }

    public String issueToken(String memberWalletAddress, String uri, String contractAlias) throws ApiException {
        Kip17TokenListResponse tokenList = caver.kas.kip17.getTokenList(contractAlias);
        String id = String.format("%#x", (tokenList.getItems().size() + 1));

        Kip17TransactionStatusResponse response = caver.kas.kip17.mint(contractAlias, memberWalletAddress, id, uri);
        log.info("issueToken :: transactionHash {}, transactionStatus{}", response.getTransactionHash(), response.getStatus());
        if (!isCommitted(response.getTransactionHash())) {
            log.warn("transaction status is Submitted : {}", response);
        }
        return response.getTransactionHash();
    }

    private boolean isCommitted(String transactionHashCode) throws ApiException {
        TransactionReceipt res = caver.kas.wallet.getTransaction(transactionHashCode);
        while (true) {
            if ("Committed" .equals(res.getStatus())) {
                return true;
            } else if ("Pending" .equals(res.getStatus())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage());
                }
            } else {
                // fail
                return false;
            }
        }
    }

}
