package com.example.corso.service;

import com.example.corso.client.DocenteClient;
import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.mapper.CorsiMapper;
import com.example.corso.response.DocenteResponse;
import com.example.corso.repository.CorsiRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorsiService {

    @Autowired
    private CorsiRepository corsiRepository;

    @Autowired
    private CorsiMapper corsiMapper;

    @Autowired
    private DocenteClient docenteClient;

    public List<CorsiDTO> findAll() {
        return corsiRepository.findAll()
                .stream()
                .map(corso -> {
                    CorsiDTO dto = corsiMapper.toDTO(corso);

                    if (corso.getDocenteId() != null) {
                        try {
                            DocenteResponse docente = docenteClient.getDocenteById(corso.getDocenteId());
                            if (docente != null) {
                                dto.setNomeDocente(docente.getNome());
                                dto.setCognomeDocente(docente.getCognome());
                            }
                        } catch (Exception e) {
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public CorsiDTO get(Long id) {
        Corsi corso = corsiRepository.findById(id).orElseThrow();

        CorsiDTO dto = corsiMapper.toDTO(corso);

        if (corso.getDocenteId() != null) {
            try {
                DocenteResponse docente = docenteClient.getDocenteById(corso.getDocenteId());
                if (docente != null) {
                    dto.setNomeDocente(docente.getNome());
                    dto.setCognomeDocente(docente.getCognome());
                }
            } catch (Exception e) {
            }
        }

        return dto;
    }

    public CorsiDTO save(CorsiDTO corsiDTO) {
        try {
            if (corsiDTO.getIdDocente() == null) {
                throw new IllegalArgumentException("idDocente mancante");
            }

            DocenteResponse docente = docenteClient.getDocenteById(corsiDTO.getIdDocente());

            Corsi corso = corsiMapper.toEntity(corsiDTO);
            corso.setDocenteId(docente.getId());

            Corsi saved = corsiRepository.save(corso);

            CorsiDTO dto = corsiMapper.toDTO(saved);
            dto.setNomeDocente(docente.getNome());
            dto.setCognomeDocente(docente.getCognome());

            return dto;

        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Docente non trovato con id: " + corsiDTO.getIdDocente());
        } catch (Exception e) {
            throw new RuntimeException("Errore generico nel salvataggio del corso", e);
        }
    }


}













