package com.example.corso.data.dto;

import com.example.corso.Response.DocenteResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CorsiDTO {

    private String nomeCorso;
    private Integer annoAccademico;
    private String nomeDocente;
    private String cognomeDocente;

    @JsonIgnore
    private DocenteResponse docenteResponse;


    public CorsiDTO(String nomeCorso, Integer annoAccademico) {

        this.nomeCorso = nomeCorso;
        this.annoAccademico = annoAccademico;

    }

    public CorsiDTO() {
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

    public DocenteResponse getDocenteResponse() {
        return docenteResponse;
    }

    public void setDocenteResponse(DocenteResponse docenteResponse) {
        this.docenteResponse = docenteResponse;
    }

    public String getNomeDocente() {
        return nomeDocente;
    }

    public void setNomeDocente(String nomeDocente) {
        this.nomeDocente = nomeDocente;
    }

    public String getCognomeDocente() {
        return cognomeDocente;
    }

    public void setCognomeDocente(String cognomeDocente) {
        this.cognomeDocente = cognomeDocente;
    }
}
