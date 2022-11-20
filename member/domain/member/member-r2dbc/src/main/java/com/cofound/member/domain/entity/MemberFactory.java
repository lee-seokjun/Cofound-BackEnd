package com.cofound.member.domain.entity;


import com.cofound.member.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberFactory {
    private final BCryptPasswordEncoder passwordEncoder;
    public Member createNewMember(MemberDto dto){
        Member member = new Member(dto);
        member.setMemberId(UUID.randomUUID().toString());
        member.setEncryptedPwd(dto.getPassword());
        return member;
    }

}
