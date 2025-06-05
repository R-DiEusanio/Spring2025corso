package com.example.corso.data.dto;

public class CorsiCreateDTO {
    private String nomeCorso;
    private Integer annoAccademico;
    private Long idDocente;
    private String nomeDocente;
    private String cognomeDocente;

    public CorsiCreateDTO() {}

    public String getNomeCorso() {
        return nomeCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public Integer getAnnoAccademico() {  // Cambiato da String a Integer
        return annoAccademico;
    }

    public void setAnnoAccademico(Integer annoAccademico) {  // Cambiato da String a Integer
        this.annoAccademico = annoAccademico;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
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