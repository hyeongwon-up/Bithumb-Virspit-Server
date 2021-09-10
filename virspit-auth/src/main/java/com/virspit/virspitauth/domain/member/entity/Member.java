package com.virspit.virspitauth.domain.member.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Member implements Serializable {
    private static final long serialVersionUID = -7353484588260422449L;

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
    private String birthdayDate;

    private Date access_dt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date updatedDate;


    @Builder
    public Member(String username, String email, String password, Gender gender, String birthdayDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.role = Role.USER;
    }
}

