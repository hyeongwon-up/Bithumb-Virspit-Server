package com.virspit.virspituser.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {
    MALE(0),
    FEMALE(1)
    ;

    private final int value;
}
