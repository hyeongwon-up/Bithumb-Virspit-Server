package com.virspit.virspitauth.service;


import com.virspit.virspitauth.dto.model.Role;
import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.feign.MemberServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final MemberServiceFeignClient memberServiceFeignClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberServiceFeignClient.findByEmail(email);

        List<GrantedAuthority> roles = new ArrayList<>();

        if (member == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        if (member.getRole()== Role.USER) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if(member.getRole() == Role.ADMIN) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new User(member.getMemberName(), member.getPassword(), roles);
    }

}
