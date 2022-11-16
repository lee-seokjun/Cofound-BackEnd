package com.cofound.member.domain.store;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.entity.Member;
import com.cofound.member.domain.entity.MemberFactory;
import com.cofound.member.domain.entity.MemberJpa;
import com.cofound.member.domain.interfaces.MemberStore;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
@Slf4j
public class MemberJpaStore implements MemberStore {

    private MemberJpa memberJpa;
    private MemberFactory memberFactory;
    private ModelMapper mapper;

    public MemberJpaStore(MemberJpa memberJpa, MemberFactory memberFactory) {
        this.memberJpa = memberJpa;
        this.memberFactory = memberFactory;
        this.mapper = new ModelMapper();
    }

    @Override
    public MemberDto createMember(MemberDto dto) {
        Member member = memberFactory.createNewMember(dto);

        return mapper.map(memberJpa.save(member),MemberDto.class);
    }

    @Override
    public MemberDto findByMemberId(String memberId) {
        return Member.toDto(memberJpa.findByMemberId(memberId));
    }

    @Override
    public Stream<MemberDto> findAllMember() {
        Iterable<Member> memberList=memberJpa.findAll();
        return StreamSupport.stream(memberList.spliterator(),false).map(c-> mapper.map(c,MemberDto.class));
    }

    @Override
    public MemberDto findByMemberEmail(String email) {
        return Member.toDto(memberJpa.findByEmail(email));
    }
    @Override
    public MemberDto findByNickName(String nickName) {
        return Member.toDto(memberJpa.findByNickName(nickName));
    }
    @Override
    @Transactional
    public void deleteMember(String memberId) {
        memberJpa.deleteByMemberId(memberId);
    }

}
