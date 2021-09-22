package com.virspit.virspitorder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.kas.tokenhistory.TokenHistoryQueryOptions;
import xyz.groundx.caver_ext_kas.kas.wallet.WalletQueryOptions;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TokenListResponse;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TransactionStatusResponse;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.tokenhistory.model.PageableNfts;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.*;

@DisplayName("Kas API 테스트")
class KasApiTest {

    private int chainId = 1001;
    private String accessKeyId = "";
    private String secretAccessKey = "";
    private CaverExtKAS caver;

    @BeforeEach
    void setUp() {
        caver = new CaverExtKAS(chainId, accessKeyId, secretAccessKey);
    }

    @Test
    void 계정_목록_조회() throws ApiException {
        WalletQueryOptions options = new WalletQueryOptions();
        Accounts accounts = caver.kas.wallet.getAccountList(options);
        System.out.println(accounts);
    }

    @Test
    void 계정_조회() throws ApiException {
        String address = "0x5C48744aC9500933c0168DBD9d4847Da1d3A3505";
        Account account = caver.kas.wallet.getAccount(address);
        System.out.println(account);
    }

    @Test
    void 계정_활성화하기() throws ApiException {
        String address = "0x5C48744aC9500933c0168DBD9d4847Da1d3A3505";
        AccountSummary enableSummary = caver.kas.wallet.enableAccount(address);

        System.out.println(enableSummary);
    }

    @DisplayName("보내는 계정이 수수료 부담")
    @Test
    void 클레이_보내기() throws ApiException {
        ValueTransferTransactionRequest request = new ValueTransferTransactionRequest();
        request.setTo("0x5C48744aC9500933c0168DBD9d4847Da1d3A3505"); // address
        request.setFrom("0x7ADe32dE57e41973EE1DCb036EFBa25F0FfedB29");
        request.setValue("0x1");
        request.setSubmit(true);

        TransactionResult transactionResult = caver.kas.wallet.requestValueTransfer(request);
        System.out.println(transactionResult);
    }

    @DisplayName("컨트랙트의 모든 토큰 조회")
    @Test
    void nft_정보_조회() throws ApiException {
        String contractAddress = "0xaa1453a5234b892284bd64aee484fb3f9cbf0fe9";
        TokenHistoryQueryOptions options = new TokenHistoryQueryOptions();
        options.setSize((long)10); // max size

        PageableNfts nfts = caver.kas.tokenHistory.getNFTList(contractAddress, options);
        System.out.println(nfts);
    }

    @DisplayName("KIP-17 컨트랙트 배포")
    @Test
    void 컨트랙트_배포() throws ApiException {
        String name = "My First KIP-17 test ";
        String symbol = "MFK";
        String alias = "my-first-kip17";
        Kip17TransactionStatusResponse res = caver.kas.kip17.deploy(name, symbol, alias);

        System.out.println(res);
    }


    @DisplayName("KIP-17 토큰 발행")
    @Test
    void 토큰_발행() throws ApiException {
        String contractAlias = "my-first-kip17";
        String to = "0x5C48744aC9500933c0168DBD9d4847Da1d3A3505";
        String id = "0x2"; // randomTokenId
        String uri = "https://metadata-store.klaytnapi.com/21d95c27-11ef-8140-64a8-19903ce3eebb/3b06f1b7-78fb-7a2c-032d-b91fb535f3a0.jpg";

        Kip17TransactionStatusResponse response = caver.kas.kip17.mint(contractAlias, to, id, uri);
        System.out.println(response);
    }

    @DisplayName("KIP-17 토큰 전송")
    @Test
    void 토큰_전송() throws ApiException {
        String contractAlias = "my-first-kip17";
        String ownerAddress = "0x{OwnerAddress}";
        String recipientAddress = "0x{RecipientAddress}";
        String tokenId = "0x321";
        Kip17TransactionStatusResponse response = caver.kas.kip17.transfer(contractAlias, ownerAddress, ownerAddress, recipientAddress, tokenId);
        System.out.println(response);
    }

    @DisplayName("GET /v1/contract/{alias-or-address}/token")
    @Test
    void 토큰_발행_목록_조회() throws ApiException {
        // 1. 0x97ce7a4a281b66054cc5cbe3343c396487ac9ed3e237ef7afe70582b6a742030
        // 2. 0x57859c7a25744780013d6fe0153d48b5b39333238e82b4e39e2a187bd909c696
        Kip17TokenListResponse response = caver.kas.kip17.getTokenList("my-first-kip17");
        System.out.println(response);
    }

    @DisplayName("트랜잭션 히스토리_유저")
    @Test
    void tokenHistory() throws ApiException {
        String contractAddress = "0xaa1453a5234b892284bd64aee484fb3f9cbf0fe9";
        String owner = "0x5C48744aC9500933c0168DBD9d4847Da1d3A3505";

        PageableNfts nfts = caver.kas.tokenHistory.getNFTListByOwner(contractAddress, owner);
        System.out.println(nfts);
        int size = nfts.getItems().size();
        System.out.println(size);
    }

}