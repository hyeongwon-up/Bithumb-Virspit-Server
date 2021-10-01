package com.virspit.virspitproduct.domain.product.feign.contract;

import com.virspit.virspitproduct.domain.product.feign.KasFeignConfig;
import com.virspit.virspitproduct.domain.product.feign.contract.request.DeployKip17ContractRequest;
import com.virspit.virspitproduct.domain.product.feign.contract.response.Kip17TransactionStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kip17", url = "https://kip17-api.klaytnapi.com/v1/contract", configuration = KasFeignConfig.class)
public interface KasContractFeignClient {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Kip17TransactionStatusResponse deployContract(@RequestBody DeployKip17ContractRequest deployKip17ContractRequest);
}
