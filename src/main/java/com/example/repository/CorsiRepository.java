package com.example.repository;

import com.example.corso.entity.Corsi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorsiRepository extends JpaRepository<Corsi, Long> {
}
