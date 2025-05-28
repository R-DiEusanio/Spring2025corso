package com.example.corso.controller;

import com.example.corso.dto.CorsiDTO;
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
        List<Corsi> corsi = corsiService.findAll();
        List<CorsiDTO> dto = corsi.stream().map(corsiMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsiDTO> getById(@PathVariable Long id) {
        Corsi corsi = corsiService.get(id);
        return ResponseEntity.ok(corsiMapper.toDTO(corsi));
    }

    @PostMapping
    public ResponseEntity<CorsiDTO> create(@RequestBody CorsiDTO dto) {
        Corsi corsi = new Corsi();
        corsi.setNomeCorso(dto.getNomeCorso());
        corsi.setAnnoAccademico(dto.getAnnoAccademico());
        return ResponseEntity.ok(corsiMapper.toDTO(corsiService.save(corsi)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsiDTO> update(@PathVariable Long id, @RequestBody CorsiDTO dto) {
        Corsi esistente = corsiService.get(id);
        esistente.setNomeCorso(dto.getNomeCorso());
        esistente.setAnnoAccademico(dto.getAnnoAccademico());
        return ResponseEntity.ok(corsiMapper.toDTO(corsiService.save(esistente)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        corsiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
