package com.cts.userBookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.cts.userBookingService.feign")
public class UserBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserBookingServiceApplication.class, args);
	}

}
