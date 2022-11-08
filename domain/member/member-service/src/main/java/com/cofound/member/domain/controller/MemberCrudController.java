package com.cofound.member.domain.controller;


import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.interfaces.MemberService;
import com.cofound.member.domain.vo.CreateMember;
import com.cofound.member.domain.vo.ResponseMember;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/member")
public class MemberCrudController {
    private final MemberService memberService;
    private final ModelMapper mapper;



    public MemberCrudController(MemberService memberServiceImpl) {
        this.memberService = memberServiceImpl;
        this.mapper = new ModelMapper();
    }

    @GetMapping
    public List<ResponseMember> findAll(){
        Stream<MemberDto> dtos =memberService.findAllMember();

        return   dtos.map(c->mapper.map(c,ResponseMember.class))
                .collect(Collectors.toList());

    }
    @GetMapping("/{memberId}")
    public ResponseMember member(@PathVariable(name="memberId") String memberId){
        MemberDto dto = memberService.findByMemberId(memberId);
        return mapper.map(dto,ResponseMember.class);
    }
    @PostMapping("/create")
    public ResponseMember createUser(@RequestBody CreateMember createMember){
        MemberDto dto =memberService.createMember(MemberDto.fromCdo(createMember));
        return mapper.map(dto,ResponseMember.class);
    }
    @DeleteMapping("/{memberId}")
    public String deleteMember(@PathVariable(name="memberId") String memberId){
        try {
            memberService.deleteMember(memberId);
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }
        return "ok";
    }
}
