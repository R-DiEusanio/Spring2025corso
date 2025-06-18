package com.example.corso.service;

import com.example.corso.client.DiscenteClient;
import com.example.corso.client.DocenteClient;
import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.data.dto.DiscenteDTO;
import com.example.corso.data.dto.DocenteDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.entity.CorsiDiscenti;
import com.example.corso.mapper.CorsiMapper;

import com.example.corso.repository.corsi.CorsiDiscentiRepository;
import com.example.corso.repository.corsi.CorsiRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import java.util.ArrayList;

@Service
public class CorsiService {

    @Autowired
    CorsiRepository corsiRepository;

    @Autowired
    CorsiMapper corsiMapper;

    @Autowired
    DocenteClient docenteClient;

    @Autowired
    DiscenteClient discenteClient;

    @Autowired
    CorsiDiscentiRepository corsiDiscentiRepository;

    public List<CorsiDTO> findAll() {
        return corsiRepository.findAll().stream()
                .map(corso -> {
                    CorsiDTO dto = corsiMapper.corsiToDto(corso);
                    loadDocenteOnCorso(dto, corso.getIdDocente());
                    loadDiscentiOnCorso(dto);
                    return dto;
                })
                .toList();
    }

    @Transactional
    public CorsiDTO save(CorsiDTO corsiDTO) {
        Long idDocente = docenteClient.createOrValidateDocente(corsiDTO.getDocenteDTO()).getIdDocente();
        Corsi corsi = corsiMapper.corsoToEntity(corsiDTO);
        corsi.setIdDocente(idDocente);
        Corsi savedCorsi = corsiRepository.save(corsi);

        saveDiscenti(corsiDTO,savedCorsi.getId());
        CorsiDTO dto = corsiMapper.corsiToDto(savedCorsi);
        loadDocenteOnCorso(dto, savedCorsi.getIdDocente());
        loadDiscentiOnCorso(dto);

        return dto;

    }

    @Transactional
    public CorsiDTO update(CorsiDTO corsiDTO) {
        Corsi corsi = corsiRepository.findCorsiById(corsiDTO.getId());

        Long idDocente = null;
        if (!Objects.isNull(corsiDTO.getDocenteDTO())) {
            idDocente = createOrValidateDocente(corsiDTO.getDocenteDTO());
        }

        if (corsi == null) {
            throw new EntityNotFoundException("Corsi non trovato");
        }
        corsi.setNomeCorso(corsiDTO.getNomeCorso());
        corsi.setAnnoAccademico(corsiDTO.getAnnoAccademico());
        corsi.setIdDocente(corsiDTO.getDocenteDTO().getIdDocente());
        Corsi savedCorsi = corsiRepository.save(corsi);
        saveDiscenti(corsiDTO, savedCorsi.getId());

        return corsiMapper.corsiToDto(savedCorsi);


        }
// delete

@Transactional
public void delete(Long id) {
    Corsi corso = corsiRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Corso con ID " + id + " non trovato."));

    List<Long> discentiAssociati = corsiDiscentiRepository.findDiscentiIdsByCorsoId(id);
    if (!discentiAssociati.isEmpty()) {

        corsiDiscentiRepository.deleteByIdCorso(id);
    }

    corsiRepository.delete(corso);
}

    // DOCENTI

    private void loadDocenteOnCorso(CorsiDTO corsoDTO, Long idDocente ) {
        corsoDTO.setDocenteDTO(docenteClient.getDocenteById(idDocente));
    }

    private Long createOrValidateDocente(DocenteDTO docenteDTO) {
        if (Objects.isNull(docenteDTO)) return null;
        else {
            DocenteDTO esistente = docenteClient.getDocenteById(docenteDTO.getIdDocente());
            Long idDocente = (esistente != null) ? esistente.getIdDocente() : null;
            if (idDocente == null) idDocente = docenteClient.createOrValidateDocente(docenteDTO).getIdDocente();
            return idDocente;

        }
    }

    // DISCENTI

    private void loadDiscentiOnCorso(CorsiDTO corsiDTO) {
        if (corsiDTO == null || corsiDTO.getId() == null) {
            System.err.println("CorsiDTO nullo o privo di ID");
            return;
        }

        Corsi corso = corsiRepository.findCorsiById(corsiDTO.getId());
        if (corso == null) {
            System.err.println("Corso non trovato per ID: " + corsiDTO.getId());
            return;
        }

        System.out.println("Caricamento discenti per corso ID: " + corso.getId());

        try {
            List<Long> discentiIds = corsiDiscentiRepository.findDiscentiIdsByCorsoId(corso.getId());
            System.out.println("Discenti trovati (ID): " + discentiIds);

            if (discentiIds == null || discentiIds.isEmpty()) {
                System.out.println("Nessun discente associato al corso");
                corsiDTO.setDiscenti(new ArrayList<>());
                return;
            }

            List<DiscenteDTO> discenti = new ArrayList<>();
            for (Long discenteId : discentiIds) {
                try {
                    System.out.println("Recupero discente con ID: " + discenteId);
                    DiscenteDTO discente = discenteClient.getDiscenteById(discenteId);
                    if (discente != null) {
                        discenti.add(discente);
                    } else {
                        System.err.println("Discente non trovato per ID: " + discenteId);
                    }
                } catch (Exception e) {
                    System.err.println("Errore nel recupero del discente con ID " + discenteId + ": " + e.getMessage());
                }
            }

            corsiDTO.setDiscenti(discenti);
            System.out.println("Totale discenti caricati: " + discenti.size());

        } catch (Exception e) {
            System.err.println("Errore generale nel caricamento dei discenti per corso ID " + corso.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void saveDiscenti(CorsiDTO corsiDTO, Long idCorso) {
    if (corsiDTO.getDiscenti() == null || corsiDTO.getDiscenti().isEmpty()) {
        return;
    }

    removeExistingDiscenti(idCorso);

    for (DiscenteDTO discenteDTO : corsiDTO.getDiscenti()) {
        try {
            DiscenteDTO esistente = null;
            if (discenteDTO.getIdDiscente() != null) {
                esistente = discenteClient.getDiscenteById(discenteDTO.getIdDiscente());
            }

            Long idDiscente;
            if (esistente == null) {
                DiscenteDTO nuovoDiscente = discenteClient.createDiscente(discenteDTO);
                idDiscente = nuovoDiscente.getIdDiscente();
            } else {
                idDiscente = esistente.getIdDiscente();
            }

            CorsiDiscenti corsiDiscenti = new CorsiDiscenti(idCorso, idDiscente);
            corsiDiscentiRepository.save(corsiDiscenti);

        } catch (Exception e) {
            System.err.println("Errore nel salvataggio discente ID: " + discenteDTO.getIdDiscente() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}

    private void removeExistingDiscenti(Long idCorso) {
        corsiDiscentiRepository.deleteByIdCorso(idCorso);
    }

}