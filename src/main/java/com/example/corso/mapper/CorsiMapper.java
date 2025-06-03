package com.example.corso.mapper;

import com.example.corso.Response.CorsiResponse;
import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.entity.Corsi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CorsiMapper {

    CorsiDTO toDTO(Corsi entity);
    Corsi toEntity(CorsiDTO dto);
    CorsiResponse toResponse(Corsi entity);
}
