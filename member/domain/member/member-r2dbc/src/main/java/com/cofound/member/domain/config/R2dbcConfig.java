package com.cofound.member.domain.config;


import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig  {

    @Bean
    public ConnectionFactoryInitializer initializer (ConnectionFactory  connectionFactory) {
        ConnectionFactoryInitializer  initializer = new ConnectionFactoryInitializer();

        initializer.setConnectionFactory(connectionFactory);
        return initializer;
    }
}
