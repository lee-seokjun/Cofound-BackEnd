package com.cofound.member.domain.service;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.interfaces.MemberService;
import com.cofound.member.domain.store.MemberJpaStore;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberJpaStore memberJpaStore;


    public MemberServiceImpl(MemberJpaStore memberJpaStore) {
        this.memberJpaStore = memberJpaStore;

    }

    @Override
    public MemberDto createMember(MemberDto dto) {


        return memberJpaStore.createMember(dto);
    }



    @Override
    public MemberDto findByMemberId(String memberId) {
        return memberJpaStore.findByMemberId(memberId);
    }



    @Override
    public Stream<MemberDto> findAllMember() {


        return memberJpaStore.findAllMember();
    }

    @Override
    public MemberDto findByMemberEmail(String email) {
        return memberJpaStore.findByMemberEmail(email);
    }

    @Override
    public void deleteMember(String memberId){
        memberJpaStore.deleteMember(memberId);
    }

    @Override
    public MemberDto findByNickName(String nickName) {
        return memberJpaStore.findByNickName(nickName);
    }
}
