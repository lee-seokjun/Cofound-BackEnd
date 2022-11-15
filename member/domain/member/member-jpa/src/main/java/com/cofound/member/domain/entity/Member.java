package com.cofound.member.domain.entity;


import com.cofound.member.domain.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name="MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(nullable = false,length = 50,unique = true,name="MEMBER_ID")
    private String memberId;
    @Column(nullable = false,length = 50,unique = true)
    private String email;
    @Column(nullable = false,length = 50,name="NICK_NAME",unique = true)
    private String nickName;
    @Column(nullable = false,length = 200)
    private String encryptedPwd;

    @Column(length = 200)
    private String prevPassword;

    @Column(name ="createdAt",nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @Column(name ="updated_at",nullable = false,  insertable = false)
    private Date updatedAt;

    @Column(name ="deleted_at",nullable=true)
    private Date deletedAt;

    @Column(name="deleted_flag",columnDefinition ="varchar(255) default 'N'")
    private String deleted_flag;
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    protected void setEncryptedPwd(String encryptedPwd) {
        this.encryptedPwd = encryptedPwd;
    }

    protected Member(MemberDto dto){
        this.email =dto.getEmail();
        this.nickName = dto.getNickName();
        this.deleted_flag ="N";
    }
}
