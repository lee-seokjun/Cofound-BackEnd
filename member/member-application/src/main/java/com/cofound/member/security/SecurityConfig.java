package com.cofound.member.security;

import com.cofound.member.domain.dto.MemberDto;
import com.cofound.member.domain.interfaces.MemberService;
import com.cofound.member.domain.service.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig  {

    private final Environment env;

    private final MemberServiceImpl memberService;
    private AuthenticationManager authenticationManager;
    public SecurityConfig(Environment env, MemberServiceImpl memberService) {
        this.env = env;
        this.memberService = memberService;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web ->
            web.ignoring()
                    .antMatchers("/any/**","/h2-console/**","/member/create/**")
        ;
    }

    @Bean
    public AuthenticationEntryPoint entryPoint(){
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
    @Bean
    public AccessDeniedHandler  deniedHandler(){
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
    @Bean
    public UserDetailsService userDetailsService(MemberService memberService ) {

        return username -> {
            MemberDto member = memberService.findByMemberEmail(username);
            Set<GrantedAuthority> grant = new HashSet<>();
            grant.add((GrantedAuthority) () -> "TESETSETEST");
            return new User(member.getEmail(),member.getEncryptedPwd(),true,true,true,true,grant);
        };


    }

    //AbstractAuthenticationProcessingFilter
    // 인증 매니저
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder encoder, UserDetailsService userDetailService)
            throws Exception {
        authenticationManager=http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(encoder)
                .and()
                .build();
        return authenticationManager;
    }



    @Bean
    public  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()

                .addFilterAt(new AuthenticationProcessingFilterConfig(memberService,env,authenticationManager).authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenFilter(env).jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint())
                .accessDeniedHandler(deniedHandler())
                .and()
                //filter 추가

                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE)
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .antMatcher("/**")

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable(); // h2-console 프레임 오류 해결 가능
        return http.build();
    }


}
