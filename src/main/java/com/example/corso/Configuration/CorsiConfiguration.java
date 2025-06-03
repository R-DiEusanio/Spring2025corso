package com.example.corso.Configuration;

import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.service.CorsiService;
import com.example.corso.mapper.CorsiMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class CorsiConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

