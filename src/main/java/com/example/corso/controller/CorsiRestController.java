package com.example.corso.controller;

import com.example.corso.data.dto.CorsiCreateDTO;
import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.mapper.CorsiMapper;
import com.example.corso.service.CorsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/corsi")
public class CorsiRestController {

    @Autowired
    private CorsiService corsiService;

    @Autowired
    private CorsiMapper corsiMapper;

    @GetMapping
    public List<CorsiDTO> getAllCorsi() {
        return corsiService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsiDTO> getById(@PathVariable Long id) {
        Corsi corsi = corsiService.get(id);
        return ResponseEntity.ok(corsiMapper.toDTO(corsi));
    }

    @PostMapping
    public ResponseEntity<Corsi> createCorso(@RequestBody CorsiCreateDTO dto) {
        Corsi saved = corsiService.saveFromDTO(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
