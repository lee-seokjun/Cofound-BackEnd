package com.cofound.member.domain.interfaces;

import com.cofound.member.domain.dto.MemberDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MemberFluxService {
    Mono<MemberDto> createMember(MemberDto dto);

    Mono<MemberDto> findByMemberId(String memberId);

    Flux<MemberDto> findAllMember();

    void deleteMember(String memberId);
    Mono<Boolean> duplicationCheckEmail(String email);
    Mono<Boolean> duplicationCheckNickName(String nickName);

    Mono<MemberDto> findByEmail(String email);

}
