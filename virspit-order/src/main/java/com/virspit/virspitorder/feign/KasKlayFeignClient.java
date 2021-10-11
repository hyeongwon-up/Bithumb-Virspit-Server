package com.virspit.virspitorder.feign;

import com.virspit.virspitorder.feign.request.KasWalletKlayTransactionRequest;
import com.virspit.virspitorder.feign.response.KasWalletKlayTransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet", url = "https://wallet-api.klaytnapi.com/v2/tx/fd-user/value", configuration = KasFeignConfig.class)
public interface KasKlayFeignClient {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    KasWalletKlayTransactionResponse deployKlayTransaction(@RequestBody KasWalletKlayTransactionRequest kasWalletKlayTransactionRequest);
}
