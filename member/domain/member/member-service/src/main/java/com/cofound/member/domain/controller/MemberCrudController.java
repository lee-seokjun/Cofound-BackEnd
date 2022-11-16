package com.cofound.member.domain.controller;


import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.interfaces.MemberService;
import com.cofound.member.domain.vo.ResponseMember;
import com.cofound.member.exception.NotFoundMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/member")
public class MemberCrudController {
    private final MemberService memberService;



    public MemberCrudController(MemberService memberServiceImpl) {
        this.memberService = memberServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ResponseMember>> findAll(){
        Stream<MemberDto> dtos =memberService.findAllMember();

        return   ResponseEntity.status(HttpStatus.OK).body(dtos.map(MemberDto::toResponse)
                .collect(Collectors.toList()));

    }
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseMember> member(@PathVariable(name="memberId") String memberId){
        try {
            MemberDto dto = memberService.findByMemberId(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(dto.toResponse());
        }catch(NotFoundMemberException e){
            log.warn("not found user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable(name="memberId") String memberId){
        try {
            memberService.deleteMember(memberId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("false");
        }
        return  ResponseEntity.status(HttpStatus.OK).body("ok");
    }
}
