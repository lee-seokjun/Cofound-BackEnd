package com.cofound.member.domain.controller;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.interfaces.MemberFluxService;
import com.cofound.member.domain.vo.CreateMember;
import com.cofound.member.domain.vo.ResponseMember;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/join")
public class JoinController {


    private final MemberFluxService service;
    private final ResponseMember emptyMember = new ResponseMember();
    public JoinController(MemberFluxService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<ResponseMember>> createUser(@RequestBody CreateMember createMember){
        return service.createMember(MemberDto.fromCdo(createMember))
                .map(MemberDto::toResponse)
                .map(it->ResponseEntity
                        .status(it.emptyCheck()? HttpStatus.BAD_REQUEST:HttpStatus.OK)
                        .body(it))
                // error handling
                // error 발생 시 badRequest & nullResponse 반환
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyMember)));

    }
    @GetMapping
    public Flux<ResponseMember> findAll(){
        return service.findAllMember()
                .map(MemberDto::toResponse);
    }
    @GetMapping("/{memberId}")
    public Mono<ResponseEntity<ResponseMember>> member(@PathVariable(name="memberId") String memberId){
        return service.findByMemberId(memberId)
                .map(MemberDto::toResponse)
                .map(it->ResponseEntity
                        .status(HttpStatus.OK)
                        .body(it))
                //빈 값을 response code로 구분하기 위해
                .defaultIfEmpty(ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(emptyMember))
                ;
    }
    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<ResponseMember>> email(@PathVariable(name="email") String email){
        return service.findByEmail(email)
                .map(MemberDto::toResponse)
                .map(it->ResponseEntity
                        .status(HttpStatus.OK)
                        .body(it))
                //빈 값을 response code로 구분하기 위해
                .onErrorReturn(ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(emptyMember));
    }
    @GetMapping("/duplicationCheck/email/{email}")
    public Mono<ResponseEntity<Boolean>> duplicateCheckEmail (@PathVariable String email){
        return service.duplicationCheckEmail(email)
                .map(it->ResponseEntity
                        .status(it?HttpStatus.OK:HttpStatus.BAD_REQUEST)
                        .body(it)
                );
    }
    @GetMapping("/duplicationCheck/nickName/{nickName}")
    public Mono<ResponseEntity<Boolean>> duplicateCheckNickName (@PathVariable String nickName){
            return service.duplicationCheckNickName(nickName)
                    .map(it->ResponseEntity
                            .status(it?HttpStatus.OK:HttpStatus.BAD_REQUEST)
                            .body(it)
                    );
    }
}
