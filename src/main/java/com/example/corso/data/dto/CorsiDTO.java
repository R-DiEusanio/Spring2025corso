package com.example.corso.data.dto;

import java.util.List;

public class CorsiDTO {

    private Long id;
    private String nomeCorso;
    private Integer annoAccademico;
    private DocenteDTO docenteDTO;

    private List<DiscenteDTO> discenti;

    public List<DiscenteDTO> getDiscenti() {
        return discenti;
    }

    public void setDiscenti(List<DiscenteDTO> discenti) {
        this.discenti = discenti;
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

    public DocenteDTO getDocenteDTO() {
        return docenteDTO;
    }

    public void setDocenteDTO(DocenteDTO docenteDTO) {
        this.docenteDTO = docenteDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}