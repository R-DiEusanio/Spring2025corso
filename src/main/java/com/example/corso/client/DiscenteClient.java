package com.example.corso.client;

import com.example.corso.data.dto.DiscenteDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DiscenteClient {

    private final WebClient webClient;

    public DiscenteClient(@Qualifier("discenteWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public DiscenteDTO getDiscenteById(Long id) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/discenti/{id}")
                            .build(id))
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il recupero del discente: " + e.getMessage());
        }
    }

    public DiscenteDTO createDiscente(DiscenteDTO discenteDTO) {
        try {
            return webClient.post()
                    .uri("/discenti")
                    .bodyValue(discenteDTO)
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la creazione del discente: " + e.getMessage());
        }
    }
}