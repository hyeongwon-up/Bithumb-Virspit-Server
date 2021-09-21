package com.virspit.virspitorder.service;

import com.klaytn.caver.methods.response.Quantity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.kas.tokenhistory.TokenHistoryQueryOptions;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.tokenhistory.model.PageableNftContractDetails;

import java.io.IOException;

@Service
public class NftService {
    private static CaverExtKAS caver = new CaverExtKAS();

    @Value("${kas.chain-id}")
    private int chainId;

    @Value("${kas.access-key-id}")
    private String accessKeyId;

    @Value("${secret-access-key}")
    private String secretAccessKey;

    private void init() throws IOException {
        caver.initNodeAPI(chainId, accessKeyId, secretAccessKey);
        Quantity response = caver.rpc.klay.getBlockNumber().send();
        System.out.println(response.getResult());

    }

    public void getNFTContractList() throws ApiException, IOException {
        init();

        // 쿼리 파라미터는 optional 파라미터이므로, 쿼리 파라미터 없이 실행할 수 있습니다.
        TokenHistoryQueryOptions options = new TokenHistoryQueryOptions();
        options.setSize((long)1);

        PageableNftContractDetails result = caver.kas.tokenHistory.getNFTContractList(options);
        System.out.println(result);
    }

}
