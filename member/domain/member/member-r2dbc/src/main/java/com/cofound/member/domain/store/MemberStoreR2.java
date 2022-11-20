package com.cofound.member.domain.store;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.entity.Member;
import com.cofound.member.domain.entity.MemberFactory;
import com.cofound.member.domain.entity.MemberRepository;
import com.cofound.member.domain.interfaces.MemberStoreMono;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MemberStoreR2 implements MemberStoreMono {
    private final MemberRepository repository;
    private final MemberFactory memberFactory;

    public MemberStoreR2(MemberRepository repository, MemberFactory memberFactory) {
        this.repository = repository;
        this.memberFactory = memberFactory;
    }

    @Override
    public Mono<MemberDto> createMember(MemberDto dto) {

        return repository.save(memberFactory.createNewMember(dto))
                .map(Member::toDto);
    }

    @Override
    public Mono<MemberDto> findByMemberId(String memberId) {
        return repository.findByMemberId(memberId)
                .map(Member::toDto);
    }
    @Override
    public Mono<MemberDto> findByNickName(String nickName) {
        return  repository.findByNickName(nickName)
                .map(Member::toDto);
    }

    @Override
    public Mono<MemberDto> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(Member::toDto);
    }
    @Override
    public Flux<MemberDto> findAllMember() {
        return repository.findAll().map(Member::toDto);
    }

    @Override
    public void deleteMember(String memberId) {

    }



}
