package com.cofound.member.domain.dto;


import com.cofound.member.domain.vo.CreateMember;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


@Data
public class MemberDto {
    private static final  ModelMapper modelMapper = new ModelMapper();
    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    private String email;
    private String nickName;
    private String memberId;
    private String password;
    private String encryptedPwd;

    public static  MemberDto fromCdo(CreateMember cdo){
        return modelMapper.map(cdo,MemberDto.class);
    }


}
