package com.example.corso.client;

import com.example.corso.data.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class DocenteClient {

    private final WebClient webClient;

    public DocenteClient(@Qualifier("docenteWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public DocenteDTO getDocenteById(Long id) {
        try {
            return webClient.get()
                    .uri("/docenti/{id}", id)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Errore durante il recupero del docente: " + e.getMessage());
        }
    }


    public DocenteDTO createOrValidateDocente(DocenteDTO docenteDTO) {
        try {
            return webClient.post()
                    .uri("/docenti")
                    .bodyValue(docenteDTO)
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la creazione del docente: " + e.getMessage());
        }
    }
}