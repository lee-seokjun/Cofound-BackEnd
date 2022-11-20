package com.cofound.member.domain.entity;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member,String> {
    Mono<Member> findByMemberId(String memberId);
    Mono<Member> findByEmail(String email);
    Mono<Member> findByNickName(String nickName);
}
