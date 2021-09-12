package com.virspit.virspituser.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자권한"),
    USER("ROLE_USER", "사용자권한"),
    UNKNOWN_USER("ROLE_UNKNOWN_USER", "알수없는 사용자");
    private final String code;
    private final String description;

    public static Role of(String code){
        return Arrays.stream(Role.values())
                .filter(i -> i.getCode().equals(code))
                .findAny()
                .orElse(UNKNOWN_USER);
    }

    public String getAuthority() {
        return name();
    }
}
