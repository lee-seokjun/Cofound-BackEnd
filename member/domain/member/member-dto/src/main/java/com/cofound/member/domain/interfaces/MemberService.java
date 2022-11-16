package com.cofound.member.domain.interfaces;

import com.cofound.member.domain.dto.MemberDto;

import java.util.stream.Stream;

public interface MemberService {
    MemberDto createMember(MemberDto dto);

    MemberDto findByMemberId(String memberId);

    Stream<MemberDto> findAllMember();

    MemberDto findByMemberEmail(String email);
    void deleteMember(String memberId);
    MemberDto findByNickName(String nickName);
}
