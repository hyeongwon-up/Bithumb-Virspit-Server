package com.virspit.virspitauth.dto.request;

import lombok.Data;

@Data
public class MemberChangePwdRequestDto {
    private Long id;
    private String beforePwd;
    private String afterPwd;
}
