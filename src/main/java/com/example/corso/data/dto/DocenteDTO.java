package com.example.corso.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocenteDTO {

    @JsonProperty("id_docente")
    private Long idDocente;
    private String nome;
    private String cognome;

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}