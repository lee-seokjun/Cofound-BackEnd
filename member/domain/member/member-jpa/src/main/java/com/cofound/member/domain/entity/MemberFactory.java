package com.cofound.member.domain.entity;


import com.cofound.member.domain.dto.MemberDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MemberFactory {
    private final BCryptPasswordEncoder encoder;

    public MemberFactory(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public Member createNewMember(MemberDto dto){
        Member member = new Member(dto);
        member.setMemberId(UUID.randomUUID().toString());
        member.setEncryptedPwd(encodePassword(dto.getPassword()));
//        member.setEncryptedPwd(dto.getPassword());
        return member;
    }

    public String encodePassword(String password){
        return encoder.encode(password);
    }


}
