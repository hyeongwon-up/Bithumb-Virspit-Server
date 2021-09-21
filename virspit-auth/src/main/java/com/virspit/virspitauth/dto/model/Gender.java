package com.virspit.virspitauth.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {
    MALE(0),
    FEMALE(1),
    ETC(2);

    private final int value;

}
