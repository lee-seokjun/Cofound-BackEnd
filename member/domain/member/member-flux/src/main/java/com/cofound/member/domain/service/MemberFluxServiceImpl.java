package com.cofound.member.domain.service;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.interfaces.MemberFluxService;
import com.cofound.member.domain.interfaces.MemberStoreMono;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class MemberFluxServiceImpl implements MemberFluxService {

    private final MemberStoreMono memberStoreMono;

    @Override
    public Mono<MemberDto> createMember(MemberDto dto) {
        return memberStoreMono.createMember(dto);
    }

    @Override
    public Mono<MemberDto> findByMemberId(String memberId) {
        return memberStoreMono.findByMemberId(memberId);
    }

    @Override
    public Flux<MemberDto> findAllMember() {
        return memberStoreMono.findAllMember();
    }

    @Override
    public void deleteMember(String memberId) {

    }

    @Override
    public Mono<Boolean> duplicationCheckEmail(String email) {
        return memberStoreMono.findByEmail(email)
                .map(MemberDto::isEmpty)
                .defaultIfEmpty(true);

    }

    @Override
    public Mono<Boolean> duplicationCheckNickName(String nickName) {
        return memberStoreMono.findByNickName(nickName)
                .map(MemberDto::isEmpty)
                .defaultIfEmpty(true);
    }

    @Override
    public Mono<MemberDto> findByEmail(String email) {
        return memberStoreMono.findByEmail(email);
    }
}
