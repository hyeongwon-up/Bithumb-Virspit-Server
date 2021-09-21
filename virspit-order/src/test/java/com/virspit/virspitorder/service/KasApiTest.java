package com.virspit.virspitorder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.kas.tokenhistory.TokenHistoryQueryOptions;
import xyz.groundx.caver_ext_kas.kas.wallet.WalletQueryOptions;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TransactionStatusResponse;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.tokenhistory.model.PageableNfts;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.*;

import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("배포하는 계정이 수수료 부담")
    @Test
    void 스마트_컨트랙트_배포() throws ApiException {
        // 배포할 스마트 컨트랙트의 바이트 코드
        String input = "0x608060405234801561001057600080fd5b5061051f806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063693ec85e1461003b578063e942b5161461016f575b600080fd5b6100f46004803603602081101561005157600080fd5b810190808035906020019064010000000081111561006e57600080fd5b82018360208201111561008057600080fd5b803590602001918460018302840111640100000000831117156100a257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506102c1565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610134578082015181840152602081019050610119565b50505050905090810190601f1680156101615780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102bf6004803603604081101561018557600080fd5b81019080803590602001906401000000008111156101a257600080fd5b8201836020820111156101b457600080fd5b803590602001918460018302840111640100000000831117156101d657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561023957600080fd5b82018360208201111561024b57600080fd5b8035906020019184600183028401116401000000008311171561026d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506103cc565b005b60606000826040518082805190602001908083835b602083106102f957805182526020820191506020810190506020830392506102d6565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103c05780601f10610395576101008083540402835291602001916103c0565b820191906000526020600020905b8154815290600101906020018083116103a357829003601f168201915b50505050509050919050565b806000836040518082805190602001908083835b6020831061040357805182526020820191506020810190506020830392506103e0565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020908051906020019061044992919061044e565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061048f57805160ff19168380011785556104bd565b828001600101855582156104bd579182015b828111156104bc5782518255916020019190600101906104a1565b5b5090506104ca91906104ce565b5090565b6104f091905b808211156104ec5760008160009055506001016104d4565b5090565b9056fea165627a7a723058203ffebc792829e0434ecc495da1b53d24399cd7fff506a4fd03589861843e14990029";

        ContractDeployTransactionRequest request = new ContractDeployTransactionRequest();
        request.setFrom("0x7ADe32dE57e41973EE1DCb036EFBa25F0FfedB29"); // 배포하는 계정 주소
        request.setInput(input);
        request.setGas(1500000L); // gas ?
        request.submit(true);

        TransactionResult transactionResult = caver.kas.wallet.requestSmartContractDeploy(request);
        System.out.println(transactionResult);
    }

    @DisplayName("컨트랙트의 모든 토큰 조회")
    @Test
    void nft_정보_조회() throws ApiException {
        String contractAddress = "0xbbe63781168c9e67e7a8b112425aa84c479f39aa";
        TokenHistoryQueryOptions options = new TokenHistoryQueryOptions();
        options.setSize((long)1);

        PageableNfts nfts = caver.kas.tokenHistory.getNFTList(contractAddress, options);
        System.out.println(nfts);
    }

    @DisplayName("KIP-17 컨트랙트 배포")
    @Test
    void 컨트랙트_배포() throws ApiException {
        String name = "My First KIP-17";
        String symbol = "MFK";
        String alias = "my-first-kip17";
        Kip17TransactionStatusResponse res = caver.kas.kip17.deploy(name, symbol, alias);
    }


    @DisplayName("KIP-17 토큰 발행")
    @Test
    void 토큰_발행() throws ApiException {
        String contractAlias = "my-first-kip17";
        String to = "0x{toAddress}";
        String id = "0x1";
        String uri = "https://link.to.your/token/metadata-0x1.json";

        Kip17TransactionStatusResponse response = caver.kas.kip17.mint(contractAlias, to, id, uri);
        System.out.println(response);
    }

    @DisplayName("KIP-17 토큰 전")
    @Test
    void 토큰_전송() throws ApiException {
        String contractAlias = "my-first-kip17";
        String ownerAddress = "0x{OwnerAddress}";
        String recipientAddress = "0x{RecipientAddress}";
        String tokenId = "0x321";
        Kip17TransactionStatusResponse response = caver.kas.kip17.transfer(contractAlias, ownerAddress, ownerAddress, recipientAddress, tokenId);
        System.out.println(response);
    }

}