package com.virspit.virspitauth.dto.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Wallet {

    private Long id;

    private String address;

    private String keyId;
}
