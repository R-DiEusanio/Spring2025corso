package com.example.corso.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DiscenteDTO {
    private String nome;
    private String cognome;
    @JsonIgnore
    private String matricola;
    @JsonIgnore
    private Integer eta;
    @JsonIgnore
    private String cittaResidenza;

    public DiscenteDTO() {}

    public DiscenteDTO(String nome, String cognome, String matricola, Integer eta, String cittaResidenza) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.eta = eta;
        this.cittaResidenza = cittaResidenza;
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

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public String getCittaResidenza() {
        return cittaResidenza;
    }

    public void setCittaResidenza(String cittaResidenza) {
        this.cittaResidenza = cittaResidenza;
    }

}