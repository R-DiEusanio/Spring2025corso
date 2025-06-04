package com.example.corso.request;

public class DocenteRequest {

    private String nome;
    private String cognome;

    public DocenteRequest() {
    }

    public DocenteRequest(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
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
