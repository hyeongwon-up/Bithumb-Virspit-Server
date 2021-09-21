package com.virspit.virspituser.domain.member.feign;

import com.virspit.virspituser.domain.member.dto.request.MemberChangePwdRequestDto;
import com.virspit.virspituser.domain.member.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("virspit-auth")
public interface AuthServiceFeignClient {

    @PutMapping(value = "/auth/changepwd", consumes = "application/json")
    Member changePassword(@RequestBody MemberChangePwdRequestDto memberChangePwdRequestDto);
}
