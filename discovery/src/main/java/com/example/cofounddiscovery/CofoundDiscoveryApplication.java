package com.example.cofounddiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CofoundDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofoundDiscoveryApplication.class, args);
	}

}
