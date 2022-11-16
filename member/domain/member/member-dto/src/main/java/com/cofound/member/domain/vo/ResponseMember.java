package com.cofound.member.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMember {
    private String email;
    private String nickName;
    private String memberId;
}
