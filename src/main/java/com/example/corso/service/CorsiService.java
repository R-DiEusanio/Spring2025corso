package com.example.corso.service;

import com.example.corso.entity.Corsi;
import com.example.repository.CorsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorsiService {

    @Autowired
    private CorsiRepository corsiRepository;

    public List<Corsi> findAll() {
        return corsiRepository.findAll();
    }

    public Corsi get(Long id) {
        return corsiRepository.findById(id).orElseThrow();
    }

    public Corsi save(Corsi corso) {
        return corsiRepository.save(corso);
    }

    public void delete(Long id) {
        corsiRepository.deleteById(id);
    }
}
