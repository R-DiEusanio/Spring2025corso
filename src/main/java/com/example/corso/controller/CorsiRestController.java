package com.example.corso.controller;

import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.mapper.CorsiMapper;
import com.example.corso.service.CorsiService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<CorsiDTO>> getAll() {
        List<CorsiDTO> corsiDTO = corsiService.findAll();
        return ResponseEntity.ok(corsiDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsiDTO> getById(@PathVariable Long id) {
        CorsiDTO corsoDTO = corsiService.get(id);
        return ResponseEntity.ok(corsoDTO);
    }

    @PostMapping
    public ResponseEntity<CorsiDTO> create(@RequestBody CorsiDTO dto) {
        CorsiDTO saved = corsiService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsiDTO> update(@PathVariable Long id, @RequestBody CorsiDTO dto) {
        CorsiDTO esistente = corsiService.get(id);

        esistente.setNomeCorso(dto.getNomeCorso());
        esistente.setAnnoAccademico(dto.getAnnoAccademico());

        if (dto.getIdDocente() != null) {
            esistente.setIdDocente(dto.getIdDocente());
        }

        CorsiDTO updated = corsiService.save(esistente);
        return ResponseEntity.ok(updated);
    }





}
