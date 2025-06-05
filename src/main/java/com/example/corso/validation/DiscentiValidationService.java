package com.example.corso.validation;

import com.example.corso.data.dto.DiscenteDTO;
import com.example.corso.repository.CorsiDiscentiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscentiValidationService {
    private static final Logger log = LoggerFactory.getLogger(DiscentiValidationService.class);
    private final WebClient webClient;
    private final CorsiDiscentiRepository corsiDiscentiRepository;

    public DiscentiValidationService(WebClient webClient, CorsiDiscentiRepository corsiDiscentiRepository) {
        this.webClient = webClient;
        this.corsiDiscentiRepository = corsiDiscentiRepository;
    }

    public List<DiscenteDTO> getDiscentiByCorsoId(Long corsoId) {
        try {
            List<Integer> discentiIds = corsiDiscentiRepository.findDiscentiIdsByCorsoId(corsoId);
            log.info("Corso ID {}: trovati {} studenti", corsoId, discentiIds.size());
            
            if (discentiIds.isEmpty()) {
                return new ArrayList<>();
            }

            String idsParam = discentiIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/discenti")
                            .queryParam("ids", idsParam)
                            .build())
                    .retrieve()
                    .bodyToFlux(DiscenteDTO.class)
                    .collectList()
                    .block();

        } catch (Exception e) {
            log.error("Errore durante il recupero dei discenti per il corso {}", corsoId, e);
            return new ArrayList<>();
        }
    }
}