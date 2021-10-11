package com.virspit.virspitorder.service;

import com.virspit.virspitorder.response.error.ErrorCode;
import com.virspit.virspitorder.response.error.exception.BusinessException;
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


    public boolean payToAdminFeesByCustomer(int price, String memberWalletAddress) {
        try {
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

            boolean isCommit = isCommitted(transactionResult.getTransactionHash());
            log.info("klay result : {}", isCommit);
            return isCommit;
        } catch (ApiException e) {
            throw new BusinessException("클레이 지불 과정에서 오류가 발생했습니다. 잔액을 확인해 주세요.", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String issueToken(String memberWalletAddress, String uri, String contractAlias) throws ApiException {
        Kip17TokenListResponse tokenList = caver.kas.kip17.getTokenList(contractAlias);
        String id = String.format("%#x", (tokenList.getItems().size() + 1));

        Kip17TransactionStatusResponse response = caver.kas.kip17.mint(contractAlias, memberWalletAddress, id, uri);
        log.info("issueToken :: transactionHash {}, transactionStatus{}", response.getTransactionHash(), response.getStatus());
        if (!isCommitted(response.getTransactionHash())) {
            log.warn("transaction status is Submitted : {}", response);
            return null;
        }
        return response.getTransactionHash();
    }

    private boolean isCommitted(String transactionHashCode) throws ApiException {
        while (true) {
            TransactionReceipt res = caver.kas.wallet.getTransaction(transactionHashCode);
            if ("Committed" .equals(res.getStatus())) {
                return true;
            } else if ("Pending" .equals(res.getStatus())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage());
                }
            } else if ("Submitted" .equals(res.getStatus())) {
                log.debug("Submitted");
            } else {
                return false;
            }
        }
    }

    public void rollBackSendKlay(Integer price, String memberWalletAddress) {
        String value = caver.utils.convertToPeb(String.valueOf(price), UNIT);
        BigInteger bi = new BigInteger(value, 10);
        String priceValue = "0x" + bi.toString(16);

        ValueTransferTransactionRequest request = new ValueTransferTransactionRequest();
        request.setTo(memberWalletAddress);
        request.setFrom(adminWalletAddress);
        request.setValue(priceValue);
        request.setSubmit(true);
    }
}
