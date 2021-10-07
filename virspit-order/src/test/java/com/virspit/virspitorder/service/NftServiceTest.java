package com.virspit.virspitorder.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.*;

import javax.xml.bind.DatatypeConverter;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NftServiceTest {

    @Autowired
    private CaverExtKAS caver;
    private String adminWalletAddress = "0x6ce57F9BF9c7C38560Ed27bddae6a3b5091B56cB";
    private String memberWalletAddress = "0xcEE008b6860Ba562e9ddba2A774842581fB74e95";

    @Test
    void 수수료내는계정() throws ApiException {
        String priceValue = (caver.utils.convertToPeb("1", "KLAY"));

        byte[] bytes = priceValue.getBytes();
        String hex = DatatypeConverter.printHexBinary(bytes);

        FDUserValueTransferTransactionRequest request = new FDUserValueTransferTransactionRequest();
        request.setFeePayer("0xf57392DEC64F0EC7859F11284eF23Db23b111d35");
        request.setFrom(adminWalletAddress);
        request.setTo(memberWalletAddress);
        request.setValue(hex);
//        request.setValue(String.format("%#x",Integer.parseInt(priceValue)));
        request.setSubmit(true);
        request.setGas(1l);
//        request.setFeeRatio(99l);

        FDTransactionResult result = caver.kas.wallet.requestFDValueTransferPaidByUser(request);
        System.out.println(result);
    }

    @Test
    void 사용자부담() throws ApiException {
        String priceValue = (caver.utils.convertToPeb("1", "KLAY"));
        BigInteger bi = new BigInteger(priceValue, 10);
        String price = "0x"+bi.toString(16);

        ValueTransferTransactionRequest request = new ValueTransferTransactionRequest();
        request.setTo(adminWalletAddress);
        request.setFrom(memberWalletAddress);
        request.setValue(price);
        request.setSubmit(true);

        TransactionResult transactionResult = caver.kas.wallet.requestValueTransfer(request);
        System.out.println(transactionResult);
    }

}