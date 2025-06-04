package com.example.corso.data.dto;

public class CorsiCreateDTO {
    private String nomeCorso;
    private Integer annoAccademico;
    private String nomeDocente;
    private String cognomeDocente;

    public String getNomeCorso() { return nomeCorso; }
    public void setNomeCorso(String nomeCorso) { this.nomeCorso = nomeCorso; }

    public Integer getAnnoAccademico() { return annoAccademico; }
    public void setAnnoAccademico(Integer annoAccademico) { this.annoAccademico = annoAccademico; }

    public String getNomeDocente() { return nomeDocente; }
    public void setNomeDocente(String nomeDocente) { this.nomeDocente = nomeDocente; }

    public String getCognomeDocente() { return cognomeDocente; }
    public void setCognomeDocente(String cognomeDocente) { this.cognomeDocente = cognomeDocente; }
}
