package com.virspit.virspituser.domain.member.dto.request;

import lombok.Data;

@Data
public class MemberChangePwdRequestDto {
    private Long id;
    private String beforePwd;
    private String afterPwd;
}
