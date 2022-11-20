package com.cofound.member.security;

import com.cofound.member.CofoundDefault;
import com.cofound.member.CofoundHeader;
import com.cofound.member.CofoundKey;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TokenFilter {
    private final Environment env;

    public TokenFilter(Environment env) {
        this.env = env;
    }

    public GenericFilterBean jwtFilter(){
        return new GenericFilterBean() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                String jwt = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
                String memberId = httpServletRequest.getHeader(CofoundHeader.MEMBER_ID);
                if(!isValidAuthHeader(jwt,memberId)){
                    log.error("header is not valid");
                }
                else{
                    jwt = jwt.replace("Bearer","");
                    String key =env.getProperty(CofoundKey.TOKEN_SECRET, CofoundDefault.DEFAULT_TOKEN_KEY);
                    UsernamePasswordAuthenticationToken authToken =getAuthentication(jwt,memberId,key);
                    if(authToken!=null)
                    {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }

                }
                filterChain.doFilter(servletRequest, servletResponse);
            }
        };
    }
    private UsernamePasswordAuthenticationToken getAuthentication(String jwt,String memberId,String key){
        try {
            String subject = Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
            if(subject==null
                    || subject.isEmpty()
                    || !subject.equals(memberId)){
                return null;
            }else{
                Set<GrantedAuthority> grant = new HashSet<>();
                grant.add((GrantedAuthority) () -> "ADMIN");
                grant.add((GrantedAuthority) () -> "USER");
                User principal = new User(subject, "", grant);

                return new UsernamePasswordAuthenticationToken(principal, jwt, grant);
            }

        }catch(Exception ex){
            return null;
        }

    }
    private boolean isValidAuthHeader(String jwt,String memberId){
        return  StringUtils.hasText(jwt)
                &&jwt.startsWith("Bearer")
                && StringUtils.hasText(memberId);
    }
}
