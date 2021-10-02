package com.virspit.virspitauth.dto.request;

import lombok.Data;

@Data
public class MemberChangePwdRequestDto {
    private String email;
    private String beforePwd;
    private String afterPwd;
}
