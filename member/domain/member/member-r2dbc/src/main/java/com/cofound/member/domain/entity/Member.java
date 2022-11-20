package com.cofound.member.domain.entity;


import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.exception.NotFoundMemberException;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;



@Table(name="MEMBER")
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Persistable<Object> {
    @Id
    private String memberId;
    private String email;
    private String nickName;
    private String encryptedPwd;

    @Override
    public Object getId() {
        return memberId;
    }

    @Transient
    private boolean newUser =false;
    public boolean isNew(){
        return this.newUser || this.memberId.length() <=0;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    protected void setEncryptedPwd(String encryptedPwd) {
        this.encryptedPwd = encryptedPwd;
    }

    protected Member(MemberDto dto){
        this.email =dto.getEmail();
        this.nickName = dto.getNickName();
        this.newUser=true;
    }
    public static MemberDto toDto(Member member){
        ModelMapper mapper = new ModelMapper();
        if(member==null){
            throw new NotFoundMemberException();
        }
        return mapper.map(member,MemberDto.class);
    }


}
