package com.virspit.virspitorder.feign;

import com.virspit.virspitorder.feign.request.KasWalletKlayTransactionRequest;
import com.virspit.virspitorder.feign.response.KasWalletKlayTransactionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KasKlayFeignClientTest {

    @Autowired
    KasKlayFeignClient client;

    @Test
    void klay() {
        String price = "0x" + Integer.toHexString(17);
        KasWalletKlayTransactionResponse result = client.deployKlayTransaction(KasWalletKlayTransactionRequest.builder()
                .feePayer("0xf57392DEC64F0EC7859F11284eF23Db23b111d35")
                .feeRatio(99)
                .from("0xcEE008b6860Ba562e9ddba2A774842581fB74e95")
                .to("0x6ce57F9BF9c7C38560Ed27bddae6a3b5091B56cB")
                .value(String.format("%#x", 17))
                .build());
        System.out.println(result);
    }
}