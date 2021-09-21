package com.virspit.virspitauth.dto.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class Member {

    private Long id;

    private String memberName;

    private String email;

    private String password;

    private Gender gender;

    private LocalDate birthdayDate;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
