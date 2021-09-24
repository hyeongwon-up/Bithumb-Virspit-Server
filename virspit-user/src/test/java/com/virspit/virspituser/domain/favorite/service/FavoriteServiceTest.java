package com.virspit.virspituser.domain.favorite.service;

import com.virspit.virspituser.domain.favorite.entity.Favorite;
import com.virspit.virspituser.domain.favorite.repository.FavoriteRepository;
import com.virspit.virspituser.domain.member.entity.Gender;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.entity.Role;
import com.virspit.virspituser.domain.member.service.MemberService;
import jnr.a64asm.Mem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTes00t {

    @Mock
    private MemberService memberService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    Member member = new Member();

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .memberName("test")
                .email("testMail")
                .birthdayDate(LocalDate.ofEpochDay(1996-12-28))
                .password("test")
                .gender(Gender.ETC)
                .build();
    }

    @Test
    void createFavorite() {
        //given
        Favorite favorite = new Favorite(member, 1L);
        given(memberService.findById(anyLong())).willReturn(member);
        given(favoriteRepository.save(any())).willReturn(favorite);

        //when
        Favorite result = favoriteService.createFavorite(1L, 1L);

        //then
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getProductId()).isEqualTo(1L);
    }

    @Test
    void deleteFavorite() {
        //given
        Favorite favorite = new Favorite(member, 1L);

        given(favoriteRepository.findFavoriteByMemberIdAndProductId(anyLong(), anyLong()))
                .willReturn(favorite);

        //when
        String result = favoriteService.deleteFavorite(1L, 1L);

        //then
        assertThat(result).isEqualTo("delete success");

    }
}