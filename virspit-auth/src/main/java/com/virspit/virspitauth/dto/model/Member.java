package com.virspit.virspitauth.dto.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Member {

    private Long id;

    private String memberName;

    private String email;

    private String password;

    private String phoneNumber;

    private Gender gender;

    private LocalDate birthdayDate;

    private Role role;

    private Wallet wallet;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
