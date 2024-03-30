package br.com.enutri.model;

import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Nutricionista extends Usuario {

    @Column (nullable = false, unique = true)
    private String CRN;

    private String formacao;

    private String especialidade;

    private String enderecoProfissional;

    @OneToMany(mappedBy = "nutricionistaResponsavel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Paciente> listaPacientes;

    public Nutricionista() {
        this.CRN = "";
        this.formacao = "";
        this.especialidade = "";
        this.enderecoProfissional = "";
    }
}
