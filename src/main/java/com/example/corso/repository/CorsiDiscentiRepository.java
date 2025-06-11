package com.example.corso.repository;

import com.example.corso.entity.CorsiDiscenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsiDiscentiRepository extends JpaRepository<CorsiDiscenti, Long> {

    @Query("SELECT cd.discenteId FROM CorsiDiscenti cd WHERE cd.idCorso = :corsoId")
    List<Long> findDiscentiIdsByCorsoId(@Param("corsoId") Long corsoId);

    void deleteByIdCorso(Long idCorso);
}
