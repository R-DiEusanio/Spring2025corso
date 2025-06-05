package com.example.corso.mapper;

import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.data.dto.DiscenteDTO;
import com.example.corso.data.dto.DocenteDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.validation.DiscentiValidationService;
import com.example.corso.validation.DocenteValidationService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {DocenteValidationService.class, DiscentiValidationService.class})
public abstract class CorsiMapper {

    @Autowired
    protected DocenteValidationService docenteValidationService;
    
    @Autowired
    protected DiscentiValidationService discentiValidationService;

    @Mapping(target = "nomeDocente", ignore = true)
    @Mapping(target = "cognomeDocente", ignore = true)
    @Mapping(target = "discenti", ignore = true)
    public abstract CorsiDTO toDTO(Corsi entity);

    @AfterMapping
    protected void afterToDTO(@MappingTarget CorsiDTO dto, Corsi entity) {
        if (entity.getIdDocente() != null) {
            try {
                DocenteDTO docente = docenteValidationService.getDocente(entity.getIdDocente());
                if (docente != null) {
                    dto.setNomeDocente(docente.getNome());
                    dto.setCognomeDocente(docente.getCognome());
                }
            } catch (Exception e) {
                dto.setNomeDocente("Non disponibile");
                dto.setCognomeDocente("Non disponibile");
            }
        }
        
        try {
            List<DiscenteDTO> discenti = discentiValidationService.getDiscentiByCorsoId(entity.getId());
            dto.setDiscenti(discenti);
        } catch (Exception e) {
            dto.setDiscenti(new ArrayList<>());
        }
    }
}