package com.example.corso.service;

import com.example.corso.data.dto.CorsiCreateDTO;
import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.data.dto.DocenteDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.mapper.CorsiMapper;
import com.example.corso.repository.CorsiRepository;
import com.example.corso.validation.DocenteValidationService;
import com.example.corso.validation.DiscentiValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorsiService {
    private static final Logger logger = LoggerFactory.getLogger(CorsiService.class);
    
    private final CorsiRepository corsiRepository;
    private final DocenteValidationService docenteValidationService;
    private final DiscentiValidationService discentiValidationService;
    private final CorsiMapper corsiMapper;

    public CorsiService(CorsiRepository corsiRepository, 
                       DocenteValidationService docenteValidationService,
                       DiscentiValidationService discentiValidationService,
                       CorsiMapper corsiMapper) {
        this.corsiRepository = corsiRepository;
        this.docenteValidationService = docenteValidationService;
        this.discentiValidationService = discentiValidationService;
        this.corsiMapper = corsiMapper;
    }

    public List<CorsiDTO> findAllDTO() {
        List<Corsi> corsi = corsiRepository.findAll();
        logger.debug("Trovati {} corsi", corsi.size());
        
        return corsi.stream()
                .map(this::convertToFullDTO)
                .collect(Collectors.toList());
    }

    private CorsiDTO convertToFullDTO(Corsi corso) {
        CorsiDTO dto = corsiMapper.toDTO(corso);
        if (corso.getId() != null) {
            dto.setDiscenti(discentiValidationService.getDiscentiByCorsoId(corso.getId()));
        }
        return dto;
    }

    public CorsiDTO getDTO(Long id) {
        logger.debug("Recupero corso con ID: {}", id);
        Corsi corso = getEntity(id);
        return convertToFullDTO(corso);
    }

    private Corsi getEntity(Long id) {
        return corsiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));
    }

    @Transactional
    public CorsiDTO saveFromDTO(CorsiCreateDTO createDTO) {
        logger.debug("Salvataggio nuovo corso: {}", createDTO.getNomeCorso());
        
        DocenteDTO docente = docenteValidationService.validateAndCreateIfNotExists(
            createDTO.getIdDocente(),
            createDTO.getNomeDocente(),
            createDTO.getCognomeDocente()
        );

        Corsi corso = new Corsi();
        corso.setNomeCorso(createDTO.getNomeCorso());
        corso.setAnnoAccademico(createDTO.getAnnoAccademico());
        corso.setIdDocente(docente.getIdDocente());

        Corsi savedCorso = corsiRepository.save(corso);
        return getDTO(savedCorso.getId());
    }

    public List<Corsi> findAll() {
        return corsiRepository.findAll();
    }

    public void delete(Long id) {
        logger.debug("Eliminazione corso con ID: {}", id);
        corsiRepository.deleteById(id);
    }

    @Transactional
    public CorsiDTO update(Long id, CorsiCreateDTO updateDTO) {
        logger.debug("Aggiornamento corso con ID: {}", id);
        
        Corsi corso = getEntity(id);
        
        DocenteDTO docente = docenteValidationService.validateAndCreateIfNotExists(
            updateDTO.getIdDocente(),
            updateDTO.getNomeDocente(),
            updateDTO.getCognomeDocente()
        );

        corso.setNomeCorso(updateDTO.getNomeCorso());
        corso.setAnnoAccademico(updateDTO.getAnnoAccademico());
        corso.setIdDocente(docente.getIdDocente());

        Corsi updatedCorso = corsiRepository.save(corso);
        return getDTO(updatedCorso.getId());
    }
}