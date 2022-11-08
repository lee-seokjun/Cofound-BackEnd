package com.cofound.member.domain.interfaces;

import com.cofound.member.domain.dto.MemberDto;

import java.util.stream.Stream;

public interface MemberStore {
    MemberDto createMember(MemberDto dto);

    MemberDto findByMemberId(String memberId);

    Stream<MemberDto> findAllMember();

    public void deleteMember(String memberId);
    public MemberDto findByMemberEmail(String email);
}
