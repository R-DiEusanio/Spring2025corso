package com.example.corso.service;

import com.example.corso.data.dto.CorsiCreateDTO;
import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.data.dto.DocenteDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.repository.CorsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
public class CorsiService {

    @Autowired
    private CorsiRepository corsiRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${docenti.service.url}")
    private String docentiServiceUrl;

    public List<CorsiDTO> findAllDTO() {
        List<Corsi> corsi = corsiRepository.findAll();

        return corsi.stream().map(corso -> {
            CorsiDTO dto = new CorsiDTO();
            dto.setId(corso.getId());
            dto.setNomeCorso(corso.getNomeCorso());
            dto.setAnnoAccademico(corso.getAnnoAccademico());

            Long docenteId = corso.getIdDocente();

            if (docenteId != null) {
                try {
                    DocenteDTO docente = getDocente(docenteId);
                    dto.setNomeDocente(docente.getNome());
                    dto.setCognomeDocente(docente.getCognome());
                } catch (Exception e) {
                    dto.setNomeDocente("Errore");
                    dto.setCognomeDocente("Errore");
                    System.out.println("Errore recuperando docente con ID: " + docenteId + " - " + e.getMessage());
                }
            } else {
                dto.setNomeDocente("Nessun");
                dto.setCognomeDocente("Docente");
            }

            return dto;
        }).toList();
    }

    public DocenteDTO getDocente(Long docenteId) {
        String url = docentiServiceUrl + "/docenti/" + docenteId;
        System.out.println("CHIAMO IL DOCENTE SU: " + url);

        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();
    }

    public Long getOrCreateDocenteId(String nome, String cognome) {
        DocenteDTO[] docentiTrovati = webClientBuilder.build()
                .get()
                .uri(docentiServiceUrl + "/docenti/search?nome={nome}&cognome={cognome}", nome, cognome)
                .retrieve()
                .bodyToMono(DocenteDTO[].class)
                .block();

        if (docentiTrovati != null && docentiTrovati.length > 0) {
            return docentiTrovati[0].getIdDocente();
        }

        DocenteDTO nuovoDocente = new DocenteDTO();
        nuovoDocente.setNome(nome);
        nuovoDocente.setCognome(cognome);

        DocenteDTO docenteCreato = webClientBuilder.build()
                .post()
                .uri(docentiServiceUrl + "/docenti")
                .bodyValue(nuovoDocente)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();

        return docenteCreato.getIdDocente();
    }

    public Corsi saveFromDTO(CorsiCreateDTO dto) {
        Long docenteId = getOrCreateDocenteId(dto.getNomeDocente(), dto.getCognomeDocente());

        Corsi corso = new Corsi();
        corso.setNomeCorso(dto.getNomeCorso());
        corso.setAnnoAccademico(dto.getAnnoAccademico());
        corso.setIdDocente(docenteId);

        return corsiRepository.save(corso);
    }

    public Corsi get(Long id) {
        return corsiRepository.findById(id).orElseThrow();
    }




}
