package com.cofound.member.domain.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/any")
public class AnyController {

    @GetMapping("/test1")
    public String test1(){
        return "test1";
    }
}
