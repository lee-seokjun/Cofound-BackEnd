package com.cofound.member.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMember {
    private String email;
    private String nickName;
    private String memberId;

    public boolean emptyCheck(){
        return Objects.isNull(memberId);
    }

}
