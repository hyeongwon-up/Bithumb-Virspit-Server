package com.virspit.virspituser.domain.member.entity;

import com.virspit.virspituser.domain.favorite.entity.Favorite;
import com.virspit.virspituser.domain.member.dto.request.MemberEditInfoRequestDto;
import com.virspit.virspituser.domain.wallet.entity.Wallet;
import com.virspit.virspituser.global.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable=false, unique=true, length=20)
    private String memberName;

    @Column(nullable=false, unique=true, length=50)
    private String email;

    @Length(min=8, max=200)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthdayDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Favorite> favoriteList = new ArrayList<>();

    @Builder
    public Member(String memberName, String email, String password, Gender gender, LocalDate birthdayDate, Wallet wallet) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.role = Role.USER;
        this.wallet = wallet;
    }

    public void editInfo(MemberEditInfoRequestDto memberEditInfoRequestDto) {
        this.memberName = memberEditInfoRequestDto.getMemberName();
        this.gender = memberEditInfoRequestDto.getGender();
        this.birthdayDate = memberEditInfoRequestDto.getBirthdayDate();
    }
}

