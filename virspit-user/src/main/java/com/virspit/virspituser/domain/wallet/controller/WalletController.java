package com.virspit.virspituser.domain.wallet.controller;

import com.virspit.virspituser.domain.wallet.entity.Wallet;
import com.virspit.virspituser.domain.wallet.repository.WalletRepository;
import com.virspit.virspituser.domain.wallet.service.WalletService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @ApiOperation("memberId 로 지갑 주소 찾기")
    @GetMapping("/{id}")
    public String findByMemberId(@PathVariable(name = "id") Long memberId) {
        return walletService.findByMemberId(memberId);
    }


}
