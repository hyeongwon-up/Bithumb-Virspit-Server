package com.virspit.virspitproduct.domain.product.feign.wallet;

import com.virspit.virspitproduct.domain.product.feign.KasFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "wallet", url = "https://wallet-api.klaytnapi.com/v2", configuration = KasFeignConfig.class)
public interface KasWalletFeignClient {
    @GetMapping("/tx/{transactionHash}")
    TransactionReceipt getTransactionReceipt(@PathVariable("transactionHash") String transactionHash);
}
