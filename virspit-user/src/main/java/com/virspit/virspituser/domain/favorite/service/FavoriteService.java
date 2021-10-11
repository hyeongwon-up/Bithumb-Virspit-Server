package com.virspit.virspituser.domain.favorite.service;

import com.virspit.virspituser.domain.favorite.dto.response.FavoriteResponseDto;
import com.virspit.virspituser.domain.favorite.entity.Favorite;
import com.virspit.virspituser.domain.favorite.repository.FavoriteRepository;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.repository.MemberRepository;
import com.virspit.virspituser.domain.member.service.MemberService;
import com.virspit.virspituser.global.error.ErrorCode;
import com.virspit.virspituser.global.error.exception.EntityNotFoundException;
import com.virspit.virspituser.global.error.exception.InvalidValueException;
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
        Member member = memberService.findById(memberId);
        Favorite favorite = new Favorite(member, productId);

        if (favoriteRepository.findFavoriteByMemberIdAndProductId(memberId, productId).isEmpty()) {
            return favoriteRepository.save(favorite);
        } else {
            throw new InvalidValueException(ErrorCode.FAVORITE_ALREADY_EXIST);
        }

    }

    public String deleteFavorite(Long memberId, Long productId) {
        Favorite favorite = favoriteRepository
                .findFavoriteByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new EntityNotFoundException(memberId.toString() + "이 좋아요 한 " + productId.toString()));
        favoriteRepository.delete(favorite);
        return "delete success";
    }

    public List<FavoriteResponseDto> getAllFavoriteByMemberId(Long memberId) {
        List<Favorite> favoriteList = favoriteRepository.findAllByMemberId(memberId);
        return FavoriteResponseDto.of(favoriteList);
    }
}
