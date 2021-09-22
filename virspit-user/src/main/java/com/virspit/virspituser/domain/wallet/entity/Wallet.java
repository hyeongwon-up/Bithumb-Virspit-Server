package com.virspit.virspituser.domain.wallet.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    private String address;

    private String keyId;

    @Builder
    public Wallet(String address, String keyId) {
        this.address = address;
        this.keyId = keyId;
    }





}
