package com.cofound.member.domain.entity;

import com.cofound.member.domain.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpa extends CrudRepository<Member,Long> {
    public Member findByEmail(String username);
    public Member findByMemberId(String memberId);

    void deleteByMemberId(String memberId);

}
