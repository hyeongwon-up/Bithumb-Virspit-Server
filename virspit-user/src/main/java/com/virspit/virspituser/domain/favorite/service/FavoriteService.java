package com.virspit.virspituser.domain.favorite.service;

import com.virspit.virspituser.domain.favorite.dto.response.FavoriteResponseDto;
import com.virspit.virspituser.domain.favorite.entity.Favorite;
import com.virspit.virspituser.domain.favorite.repository.FavoriteRepository;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.repository.MemberRepository;
import com.virspit.virspituser.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Favorite createFavorite(Long memberId, Long productId) {
        Member member =  memberService.findById(memberId);
        Favorite favorite = new Favorite(member, productId);
        return favoriteRepository.save(favorite);
    }

    public String deleteFavorite(Long memberId, Long productId) {
        Favorite favorite = favoriteRepository
                .findFavoriteByMemberIdAndProductId(memberId, productId);
        favoriteRepository.delete(favorite);
        return "delete success";
    }

    public List<FavoriteResponseDto> getAllFavoriteByMemberId(Long memberId) {
        List<Favorite> favoriteList = favoriteRepository.findAllByMemberId(memberId);
        return FavoriteResponseDto.of(favoriteList);
    }
}
