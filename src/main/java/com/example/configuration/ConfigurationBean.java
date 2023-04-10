package com.example.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("!local")
@Configuration
@PropertySource("classpath:application.properties")
public class ConfigurationBean {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
