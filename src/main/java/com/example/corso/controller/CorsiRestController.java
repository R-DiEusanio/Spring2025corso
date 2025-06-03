package com.example.corso.controller;

import com.example.corso.Response.CorsiResponse;
import com.example.corso.data.dto.CorsiDTO;
import com.example.corso.entity.Corsi;
import com.example.corso.mapper.CorsiMapper;
import com.example.corso.service.CorsiService;
import com.example.corso.repository.CorsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/corsi")
public class CorsiRestController {

    @Autowired
    private CorsiService corsiService;

    @Autowired
    private CorsiRepository corsiRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CorsiMapper corsiMapper;

    @GetMapping
    public ResponseEntity<List<CorsiDTO>> getCorsiDTO() {
        List<CorsiDTO> corsiDTO = corsiService.getAllCorsi();
        return new ResponseEntity<>(corsiDTO, HttpStatus.OK);
    }


    @GetMapping("/corsi")
    public ResponseEntity<?> getCorsiByGetForObject() {

        String URI_CORSI = "http://localhost:8081/corsi";

        Corsi[] CorsiData = restTemplate.getForObject(URI_CORSI,Corsi[].class);

        return new ResponseEntity<>(Arrays.asList(CorsiData), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CorsiDTO> creaCorso(@RequestBody CorsiDTO dto) {
        CorsiDTO creato = corsiService.createCorso(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }


}
