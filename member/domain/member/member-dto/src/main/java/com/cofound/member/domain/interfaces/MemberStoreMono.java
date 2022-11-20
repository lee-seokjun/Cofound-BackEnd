package com.cofound.member.domain.interfaces;

import com.cofound.member.domain.dto.MemberDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MemberStoreMono {
    Mono<MemberDto> createMember(MemberDto dto);

    Mono<MemberDto> findByMemberId(String memberId);

    Mono<MemberDto> findByEmail(String email);

    Mono<MemberDto> findByNickName(String nickName);

    Flux<MemberDto> findAllMember();

    void deleteMember(String memberId);




}
