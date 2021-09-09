package com.virspit.virspitauth.domain.user.entity;


import com.virspit.virspitauth.global.code.Gender;
import com.virspit.virspitauth.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "user_password")
    @Size(min=8, message = "8자리 이상 입력하세요")
    private String password;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "accout_birthdayDate", nullable = false)
    private LocalDate birthdayDate;

    @ElementCollection(fetch = FetchType.EAGER)
    public List<Role> roles;
}
