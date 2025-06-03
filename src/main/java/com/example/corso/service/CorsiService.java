package com.example.corso.service;

import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.Response.DocenteResponse;
import com.example.corso.entity.Corsi;
import com.example.corso.mapper.CorsiMapper;
import com.example.corso.repository.CorsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CorsiService {

    @Autowired
    private CorsiRepository corsiRepository;

    @Autowired
    private CorsiMapper corsiMapper;

    @Autowired
    private RestTemplate restTemplate;

    public List<CorsiDTO> getAllCorsi() {
        List<Corsi> corsiList = corsiRepository.findAll();
        List<CorsiDTO> dtoList = new ArrayList<>();

        for (Corsi corso : corsiList) {
            CorsiDTO dto = corsiMapper.toDTO(corso);
            Long docenteId = corso.getIdDocente();

            if (docenteId != null) {
                try {
                    DocenteResponse docente = restTemplate.getForObject(
                            "http://localhost:8080/docenti/{id}",
                            DocenteResponse.class,
                            docenteId
                    );

                    if (docente != null) {
                        dto.setNomeDocente(docente.getNome());
                        dto.setCognomeDocente(docente.getCognome());
                    }

                } catch (Exception e) {
                    System.out.println("Errore nel recupero del docente con id: " + docenteId);
                    e.printStackTrace();
                }
            }

            dtoList.add(dto);
        }

        return dtoList;
    }



    public CorsiDTO createCorso(CorsiDTO dto) {
        if (dto.getDocenteResponse() == null) {
            throw new RuntimeException("ID docente mancante");
        }

        DocenteResponse docenteResponse = restTemplate.getForObject(
                "http://localhost:8080/docenti/{id}",
                DocenteResponse.class,
                dto.getDocenteResponse()
        );

        if (docenteResponse == null) {
            throw new RuntimeException("Docente non trovato con id: " + dto.getDocenteResponse());
        }

        Corsi corsi = new Corsi();
        corsi.setNomeCorso(dto.getNomeCorso());
        corsi.setAnnoAccademico(dto.getAnnoAccademico());
        corsi.setIdDocente(dto.getDocenteResponse().getId());

        Corsi salvato = corsiRepository.save(corsi);

        CorsiDTO response = corsiMapper.toDTO(salvato);
        response.setDocenteResponse(docenteResponse);

        return response;
    }

    public List<Corsi> findAll() {
        return corsiRepository.findAll();
    }

    public Corsi get(Long id) {
        return corsiRepository.findById(id).orElseThrow();
    }

    public Corsi save(Corsi corsi) {
        return corsiRepository.save(corsi);
    }

    public void delete(Long id) {
        corsiRepository.deleteById(id);
    }



}
