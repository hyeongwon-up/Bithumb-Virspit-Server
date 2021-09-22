package com.virspit.virspituser.domain.member.dto.request;

import com.virspit.virspituser.domain.member.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberEditInfoRequestDto {
    private String memberName;
    private Gender gender;
    private LocalDate birthdayDate;
}
