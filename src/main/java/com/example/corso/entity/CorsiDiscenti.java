package com.example.corso.entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "corsi_discenti")
public class CorsiDiscenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "id_corso")
    private Long idCorso;

    @Column(name = "discente_id")
    private Long discenteId;
    
    public CorsiDiscenti() {}
    
    public CorsiDiscenti(Long idCorso, Long discenteId) {
        this.idCorso = idCorso;
        this.discenteId = discenteId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCorso() {
        return idCorso;
    }

    public void setIdCorso(Long idCorso) {
        this.idCorso = idCorso;
    }

    public Long getDiscenteId() {
        return discenteId;
    }
    
    public void setDiscenteId(Long DiscenteId) {
        this.discenteId = DiscenteId;
    }

}