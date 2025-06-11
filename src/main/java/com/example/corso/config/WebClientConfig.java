
package com.example.corso.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${discente.service.url}")
    private String discenteServiceUrl;

    @Value("${docente.service.url}")
    private String docenteServiceUrl;

    @Bean
    @Qualifier("discenteWebClient")
    public WebClient discenteWebClient() {
        return WebClient.builder()
                .baseUrl(discenteServiceUrl)
                .build();
    }

    @Bean
    @Qualifier("docenteWebClient")
    public WebClient docenteWebClient() {
        return WebClient.builder()
                .baseUrl(docenteServiceUrl)
                .build();
    }
}
