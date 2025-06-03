package com.example.corso.entity;

import com.example.corso.Response.DocenteResponse;
import jakarta.persistence.*;

@Entity
@Table(name = "corsi")
public class Corsi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_corso", nullable = false)
    private String nomeCorso;

    @Column(name = "anno_accademico", nullable = false)
    private Integer annoAccademico;

    @Column(name = "id_docente",nullable = false)
    private Long idDocente;

    public Corsi() {}

    public Corsi(String nomeCorso, Integer annoAccademico) {
        this.nomeCorso = nomeCorso;
        this.annoAccademico = annoAccademico;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public Integer getAnnoAccademico() {
        return annoAccademico;
    }

    public void setAnnoAccademico(Integer annoAccademico) {
        this.annoAccademico = annoAccademico;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

}
