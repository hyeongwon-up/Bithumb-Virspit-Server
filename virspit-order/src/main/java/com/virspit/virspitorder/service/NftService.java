package com.virspit.virspitorder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TokenListResponse;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TransactionStatusResponse;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.TransactionResult;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.ValueTransferTransactionRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class NftService {
    private final static String UNIT = "KLAY";
    private final CaverExtKAS caver;

    @Value("${kas.admin-wallet-address}")
    private String adminWalletAddress;

    public boolean payToAdminFeesByCustomer(int price, String memberWalletAddress) throws ApiException {
        String priceValue = caver.utils.convertToPeb(String.valueOf(price), UNIT);

        ValueTransferTransactionRequest request = new ValueTransferTransactionRequest();
        request.setTo(adminWalletAddress);
        request.setFrom(memberWalletAddress);
        request.setValue(priceValue);
        request.setSubmit(true);

        TransactionResult transactionResult = caver.kas.wallet.requestValueTransfer(request);

        log.info("transactionResult:: transactionHash {}, transactionStatus {}",
                transactionResult.getTransactionHash(),
                transactionResult.getStatus());
        return true;
    }

    public String issueToken(String memberWalletAddress, String uri, String contractAlias) throws ApiException {
        Kip17TokenListResponse tokenList = caver.kas.kip17.getTokenList(contractAlias);
        String id = Integer.toHexString(tokenList.getItems().size() + 1);

        Kip17TransactionStatusResponse response = caver.kas.kip17.mint(contractAlias, memberWalletAddress, id, uri);
        log.info("issueToken :: transactionHash {}, transactionStatus{}", response.getTransactionHash(), response.getStatus());

        return response.getTransactionHash();
    }

}
