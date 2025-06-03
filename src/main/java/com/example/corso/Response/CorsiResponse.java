package com.example.corso.Response;

public class CorsiResponse {

    private Integer id;
    private String nomeCorso;
    private Integer annoAccademico;

    private DocenteResponse docenteResponse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public DocenteResponse getDocenteResponse() {
        return docenteResponse;
    }

    public void setDocenteResponse(DocenteResponse docenteResponse) {
        this.docenteResponse = docenteResponse;
    }
}
