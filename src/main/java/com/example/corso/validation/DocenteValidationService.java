package com.example.corso.validation;

import com.example.corso.data.dto.DocenteDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;

@Service
public class DocenteValidationService {
    private final WebClient webClient;

    public DocenteValidationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public DocenteDTO validateAndCreateIfNotExists(Long idDocente, String nome, String cognome) {
        try {
            return webClient.get()
                    .uri("/docenti/" + idDocente)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            if (nome != null && !nome.isEmpty() && cognome != null && !cognome.isEmpty()) {
                DocenteDTO nuovoDocente = new DocenteDTO();
                nuovoDocente.setNome(nome);
                nuovoDocente.setCognome(cognome);
                
                return createDocente(nuovoDocente);
            } else {
                throw new RuntimeException("Docente non trovato con id: " + idDocente + " e non sono stati forniti nome e cognome per crearlo");
            }
        }
    }

    public boolean validateDocente(Long idDocente) {
        try {
            DocenteDTO docente = webClient.get()
                    .uri("/docenti/" + idDocente)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
            return docente != null;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        }
    }

    public DocenteDTO getDocente(Long idDocente) {
        try {
            return webClient.get()
                    .uri("/docenti/" + idDocente)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Docente non trovato con id: " + idDocente);
        }
    }

    public DocenteDTO createDocente(DocenteDTO docente) {
        try {
            return webClient.post()
                    .uri("/docenti")
                    .bodyValue(docente)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Errore durante la creazione del docente: " + e.getMessage());
        }
    }

    public List<DocenteDTO> getAllDocenti() {
        try {
            return webClient.get()
                    .uri("/docenti")
                    .retrieve()
                    .bodyToFlux(DocenteDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Errore durante il recupero dei docenti: " + e.getMessage());
        }
    }

    public void deleteDocente(Long idDocente) {
        try {
            webClient.delete()
                    .uri("/docenti/" + idDocente)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Docente non trovato con id: " + idDocente);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Errore durante l'eliminazione del docente: " + e.getMessage());
        }
    }

    public DocenteDTO updateDocente(Long idDocente, DocenteDTO docente) {
        try {
            return webClient.put()
                    .uri("/docenti/" + idDocente)
                    .bodyValue(docente)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Docente non trovato con id: " + idDocente);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Errore durante l'aggiornamento del docente: " + e.getMessage());
        }
    }
}