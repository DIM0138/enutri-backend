package br.com.enutri.model;

import lombok.*;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Nutricionista extends Usuario {

    @Column (nullable = false, unique = true)
    private String CRN;

    @Column (nullable = false)
    private String formacao;

    private String especialidade;
    
    private String enderecoProfissional;

    private List<Paciente> listaPacientes;
}
