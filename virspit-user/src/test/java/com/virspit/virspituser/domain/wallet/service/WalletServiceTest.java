package com.virspit.virspituser.domain.wallet.service;

import com.virspit.virspituser.domain.wallet.entity.Wallet;
import com.virspit.virspituser.domain.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private CaverExtKAS caverExtKAS;

    @InjectMocks
    private WalletService walletService;

    @Test
    void 지갑_생성() throws ApiException {

        Wallet wallet = walletService.createWallet();

        System.out.println(wallet.toString());
    }

}