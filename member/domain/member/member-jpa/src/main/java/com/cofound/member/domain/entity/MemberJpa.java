package com.cofound.member.domain.entity;

import com.cofound.member.domain.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpa extends CrudRepository<Member,String> {
    Member findByEmail(String username);
    Member findByNickName(String nickName);
    Member findByMemberId(String memberId);

    void deleteByMemberId(String memberId);

}
