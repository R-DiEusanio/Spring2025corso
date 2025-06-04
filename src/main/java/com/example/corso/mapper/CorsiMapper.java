package com.example.corso.mapper;

import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.entity.Corsi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CorsiMapper {

    @Mapping(source = "docenteId", target = "idDocente")
    CorsiDTO toDTO(Corsi entity);

    @Mapping(source = "idDocente", target = "docenteId")
    Corsi toEntity(CorsiDTO dto);

    List<CorsiDTO> toDTOList(List<Corsi> entities);
    List<Corsi> toEntityList(List<CorsiDTO> dtos);
}
