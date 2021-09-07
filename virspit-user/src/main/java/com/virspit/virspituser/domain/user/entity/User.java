package com.virspit.virspituser.domain.user.entity;

import com.virspit.virspituser.global.code.Gender;
import com.virspit.virspituser.global.code.Role;
import com.virspit.virspituser.global.entity.BaseTimeEntity;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "accout_birthdayDate", nullable = false)
    private LocalDate birthdayDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    public Role role;


}
