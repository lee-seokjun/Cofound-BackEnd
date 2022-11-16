package com.cofound.member.domain.controller;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.interfaces.MemberService;
import com.cofound.member.domain.vo.CreateMember;
import com.cofound.member.domain.vo.ResponseMember;
import com.cofound.member.exception.NotFoundMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/join")
public class CreateController {
    private final MemberService memberService;

    public CreateController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/duplicationCheck/nickName/{nickName}")
    public ResponseEntity<Boolean> duplicateCheckNickName (@PathVariable String nickName){
        try {
            memberService.findByNickName(nickName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }catch(NotFoundMemberException e)
        {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }

    }

    @GetMapping("/duplicationCheck/email/{email}")
    public ResponseEntity<Boolean> duplicateCheckEmail (@PathVariable String email){
        try {
            memberService.findByMemberEmail(email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }catch(NotFoundMemberException e)
        {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }
    @PostMapping
    public ResponseEntity<ResponseMember> createUser(@RequestBody CreateMember createMember){
        MemberDto dto =memberService.createMember(MemberDto.fromCdo(createMember));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.toResponse());
    }
}
