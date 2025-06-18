package com.example.corso.repository.corsi;

import com.example.corso.entity.Corsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CorsiRepository extends JpaRepository<Corsi, Long> {


    @Query("SELECT c FROM Corsi c WHERE c.id = :id")
    Corsi findCorsiById(@Param("id") Long id);

}