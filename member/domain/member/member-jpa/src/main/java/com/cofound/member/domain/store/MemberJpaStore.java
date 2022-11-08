package com.cofound.member.domain.store;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.entity.Member;
import com.cofound.member.domain.entity.MemberFactory;
import com.cofound.member.domain.entity.MemberJpa;
import com.cofound.member.domain.interfaces.MemberStore;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
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
        return mapper.map(memberJpa.findByMemberId(memberId),MemberDto.class);
    }

    @Override
    public Stream<MemberDto> findAllMember() {
        return StreamSupport.stream(memberJpa.findAll().spliterator(),false).map(c-> mapper.map(c,MemberDto.class));
    }

    @Override
    public MemberDto findByMemberEmail(String email) {
        return mapper.map(memberJpa.findByEmail(email),MemberDto.class);
    }

    @Override
    @Transactional
    public void deleteMember(String memberId) {
        memberJpa.deleteByMemberId(memberId);
    }

}
