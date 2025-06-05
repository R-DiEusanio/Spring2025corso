package com.example.corso.entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "corsi_discenti", uniqueConstraints = {
    @UniqueConstraint(name = "uk_corso_discente", columnNames = {"corso_id", "discente_id"})
})
public class CorsiDiscenti implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corso_id", nullable = false)
    private Corsi corsi;
    
    @Column(name = "discente_id", nullable = false)
    private Integer discenteId;
    
    public CorsiDiscenti() {}
    
    public CorsiDiscenti(Corsi corsi, Integer discenteId) {
        this.corsi = corsi;
        this.discenteId = discenteId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Corsi getCorso() {
        return corsi;
    }
    
    public void setCorso(Corsi corsi) {
        this.corsi = corsi;
    }
    
    public Integer getDiscenteId() {
        return discenteId;
    }
    
    public void setDiscenteId(Integer studenteId) {
        this.discenteId = studenteId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorsiDiscenti that = (CorsiDiscenti) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(corsi, that.corsi) &&
               Objects.equals(discenteId, that.discenteId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, corsi, discenteId);
    }
    
    @Override
    public String toString() {
        return "CorsiStudenti{" +
               "id=" + id +
               ", corso=" + (corsi != null ? corsi.getId() : null) +
               ", studenteId=" + discenteId +
               '}';
    }
}