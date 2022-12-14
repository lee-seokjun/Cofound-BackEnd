package com.cofound.gateway.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;
    private final String UserHEADER = "userId";
    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }
    public static class Config{

    }
    //login -> token -> users (with token) -> header(include token)
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain)->
        {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange,"no authoriztion header", HttpStatus.UNAUTHORIZED);
            }
            if(!request.getHeaders().containsKey(UserHEADER)){
                return onError(exchange,"no userId header", HttpStatus.UNAUTHORIZED);
            }
            String authoriztionHeader = request.getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authoriztionHeader.replace("Bearer","");
            String userId = request.getHeaders().get(UserHEADER).get(0);
            if(!isJWtValid(jwt,userId)){
                return onError(exchange,"JWT token is not valid ", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }
    // Mono, Flux -> spring webFlux ?????? ??????
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    private boolean isJWtValid(String jwt, String userId) {
        boolean returnValue =true;

        String subject =null;

        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        }catch(Exception ex){
            returnValue=false;
        }

        if(subject==null || subject.isEmpty() || !userId.equals(subject)){
            returnValue=false;
        }


        return returnValue;

    }
}
