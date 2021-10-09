package com.virspit.virspitorder.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "MemberResponseDto", description = "회 응답 DTO")
@Getter
public class MemberResponseDto {
    private String birthdayDate;
    private String email;
    private String memberName;
    private String gender;
    private String phoneNumber;
    private String role;
}
