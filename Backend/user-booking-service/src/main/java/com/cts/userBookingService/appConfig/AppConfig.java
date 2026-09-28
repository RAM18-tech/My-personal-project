package com.cts.userBookingService.appConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // 🔧 TODO: Add more beans if needed (e.g. Feign config, CORS, etc.)
}
