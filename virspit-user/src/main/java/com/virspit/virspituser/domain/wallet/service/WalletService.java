package com.virspit.virspituser.domain.wallet.service;

import com.virspit.virspituser.domain.member.repository.MemberRepository;
import com.virspit.virspituser.domain.wallet.entity.Wallet;
import com.virspit.virspituser.domain.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.Account;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final MemberRepository memberRepository;
    private final CaverExtKAS caverExtKAS;


    public Wallet createWallet() throws ApiException {

        Account account = caverExtKAS.kas.wallet.createAccount();

        Wallet wallet = Wallet.builder()
                .address(account.getAddress())
                .keyId(account.getKeyId())
                .build();

        return walletRepository.save(wallet);

    }

    public String findByMemberId(Long memberId) {
        return memberRepository.findById(memberId).get().getWallet().getAddress();
    }

}
