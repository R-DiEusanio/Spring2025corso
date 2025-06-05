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

@RestController
@RequestMapping("/corsi")
public class CorsiRestController {

    @Autowired
    private CorsiService corsiService;

    @GetMapping
    public List<CorsiDTO> getAllCorsi() {
        return corsiService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsiDTO> getById(@PathVariable Long id) {
        CorsiDTO corso = corsiService.getDTO(id);
        return ResponseEntity.ok(corso);
    }

    @PostMapping
    public ResponseEntity<CorsiDTO> createCorso(@RequestBody CorsiCreateDTO dto) {
        CorsiDTO saved = corsiService.saveFromDTO(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsiDTO> updateCorso(@PathVariable Long id, @RequestBody CorsiCreateDTO dto) {
        CorsiDTO updated = corsiService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorso(@PathVariable Long id) {
        corsiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}