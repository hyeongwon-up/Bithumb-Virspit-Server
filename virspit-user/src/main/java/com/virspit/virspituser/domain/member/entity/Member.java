package com.virspit.virspituser.domain.member.entity;

import com.virspit.virspituser.global.code.Gender;
import com.virspit.virspituser.global.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=20)
    private String username;

    @Column(nullable=false, unique=true, length=50)
    private String email;

    @Length(min=8, max=200)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthdayDate;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public Member(String username, String email, String password, Gender gender, LocalDate birthdayDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.role = Role.USER;
    }
}

