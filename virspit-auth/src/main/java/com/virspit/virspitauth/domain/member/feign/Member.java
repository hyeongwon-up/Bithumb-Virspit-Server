package com.virspit.virspitauth.domain.member.feign;

import com.virspit.virspitauth.domain.member.entity.Gender;
import com.virspit.virspitauth.domain.member.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class Member {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Gender gender;

    private LocalDate birthdayDate;

    private Role role;

}
