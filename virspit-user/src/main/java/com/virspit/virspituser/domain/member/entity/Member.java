package com.virspit.virspituser.domain.member.entity;

import com.virspit.virspituser.domain.favorite.entity.Favorite;
import com.virspit.virspituser.domain.member.dto.request.MemberEditInfoRequestDto;
import com.virspit.virspituser.domain.wallet.entity.Wallet;
import com.virspit.virspituser.global.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.web3j.protocol.admin.Admin;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(nullable=false, length=20)
    @NotNull(message = "이름을 입력해주세요.")
    private String memberName;

    @Column(nullable=false, unique=true, length=50)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
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
    public Member(String memberName, String email, String password, Gender gender, LocalDate birthdayDate,Role role, Wallet wallet) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.role = role;
        this.wallet = wallet;
    }

    public void editInfo(MemberEditInfoRequestDto memberEditInfoRequestDto) {
        this.memberName = memberEditInfoRequestDto.getMemberName();
        this.gender = memberEditInfoRequestDto.getGender();
        this.birthdayDate = memberEditInfoRequestDto.getBirthdayDate();
        this.phoneNumber = memberEditInfoRequestDto.getPhoneNumber();
    }

    public void changePwd(final String password) {
        this.password = password;
    }

}

