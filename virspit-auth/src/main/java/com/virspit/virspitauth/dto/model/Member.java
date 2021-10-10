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

    @Builder
    public Member(Long id, String memberName, String email, String password, String phoneNumber, Gender gender, LocalDate birthdayDate, Wallet wallet) {
        this.id = id;
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.role = Role.USER;
        this.wallet = wallet;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


}
