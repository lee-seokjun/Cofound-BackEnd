package com.cofound.member.domain.interfaces;

import com.cofound.member.domain.dto.MemberDto;

import java.util.stream.Stream;

public interface MemberStore {
    MemberDto createMember(MemberDto dto);

    MemberDto findByMemberId(String memberId);

    Stream<MemberDto> findAllMember();

    void deleteMember(String memberId);
    MemberDto findByMemberEmail(String email);
    MemberDto findByNickName(String nickName);
}
