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

    public CorsiDTO getCorsyById(Long id) {

        Corsi corsi = corsiRepository.findById(id).orElseThrow(() -> new RuntimeException("corso non trovato con id: " +id));

        CorsiDTO corsiDTO = corsiMapper.toDTO(corsi);

        DocenteResponse docenteResponse = restTemplate.getForObject("http://localhost:8080/docenti", DocenteResponse.class,docentId);

        corsiDTO.setDocenteResponse(docenteResponse);
        return corsiDTO;

    }

    public List<Corsi> findAll() {
        return corsiRepository.findAll();
    }

    public Corsi get(Long id) {
        return corsiRepository.findById(id).orElseThrow();
    }

    public Corsi save(Corsi corso) {
        return corsiRepository.save(corso);
    }

    public void delete(Long id) {
        corsiRepository.deleteById(id);
    }



}
