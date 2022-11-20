package com.cofound.member.security;

import com.cofound.member.CofoundDefault;
import com.cofound.member.CofoundHeader;
import com.cofound.member.CofoundKey;
import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.service.MemberServiceImpl;
import com.cofound.member.exception.NotFoundMemberException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Component
public class AuthenticationProcessingFilterConfig {
    //AbstractAuthenticationProcessingFilter를 만들기 위한 빌드다..
    private final MemberServiceImpl memberService;
    private final Environment env;
    private final AuthenticationManager auth;



    public AuthenticationProcessingFilterConfig(MemberServiceImpl memberService , Environment env, AuthenticationManager auth) {
        this.memberService = memberService;
        this.env = env;
        this.auth = auth;
    }



    public  AbstractAuthenticationProcessingFilter authenticationFilter() {
        AbstractAuthenticationProcessingFilter filter= new AbstractAuthenticationProcessingFilter(new AntPathRequestMatcher("/login", "POST")) {
            @Override
            public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                    throws AuthenticationException {
                try {
                    RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
                    Set<GrantedAuthority> grant = new HashSet<>();
                    grant.add((GrantedAuthority) () -> "ADMIN");
                    grant.add((GrantedAuthority) () -> "USER");
                    return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(),grant));
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void successfulAuthentication(HttpServletRequest request
                    , HttpServletResponse response
                    , FilterChain chain
                    , Authentication authResult) {
                String email = ((User)authResult.getPrincipal()).getUsername();
                MemberDto userDto;
                try {
                     userDto = memberService.findByMemberEmail(email);
                }catch(NotFoundMemberException e)
                {
                    return;
                }
                String token = Jwts.builder()
                        .setSubject(userDto.getMemberId())
                        .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty(CofoundKey.TOKEN_EXPIRATION_TIME,CofoundDefault.DEFAULT_TOKEN_EXPIRATION_TIME))))
                        .signWith(SignatureAlgorithm.HS512,env.getProperty(CofoundKey.TOKEN_SECRET, CofoundDefault.DEFAULT_TOKEN_KEY))
                        .compact();
                response.addHeader("token",token);
                response.addHeader(CofoundHeader.MEMBER_ID,userDto.getMemberId());

                ResponseCookie cookie =  ResponseCookie
                        .from("refreshToken",token)
                        .maxAge(7 * 24 * 60 * 60)
                        .path("/")
                        .secure(true)
                        .sameSite("None")
                        .httpOnly(true)
                        .build();
                response.addHeader("Set-Cookie",cookie.toString());
            }
        };

        filter.setAuthenticationManager(auth);
        return filter;
    }

}
