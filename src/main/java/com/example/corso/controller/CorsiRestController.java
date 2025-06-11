package com.example.corso.controller;

import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.data.dto.DiscenteDTO;
import com.example.corso.service.CorsiService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/corsi")
public class CorsiRestController {

    @Autowired
    CorsiService corsiService;

    @GetMapping
    public ResponseEntity<List<CorsiDTO>> getAllCorsi() {
        return ResponseEntity.ok(corsiService.findAll());
    }

    @PostMapping
    public ResponseEntity<CorsiDTO> saveCorsi(@RequestBody CorsiDTO corsiDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(corsiService.save(corsiDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsiDTO> updateCorsi(@PathVariable Long id, @RequestBody CorsiDTO corsiDTO) {
        return ResponseEntity.ok(corsiService.update(corsiDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteCorsi(@PathVariable Long id) {
        corsiService.delete(id);
    }




}
