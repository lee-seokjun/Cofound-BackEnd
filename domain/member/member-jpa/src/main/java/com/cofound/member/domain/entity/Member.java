package com.cofound.member.domain.entity;


import com.cofound.member.domain.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name="MEMBER")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 50,unique = true)
    private String email;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false,length = 50,unique = true)
    private String memberId;

    @Column(nullable = false,length = 200,unique = true)
    private String encryptedPwd;

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    protected void setEncryptedPwd(String encryptedPwd) {
        this.encryptedPwd = encryptedPwd;
    }

    protected Member(MemberDto dto){
        this.email =dto.getEmail();
        this.name = dto.getName();

    }
}
