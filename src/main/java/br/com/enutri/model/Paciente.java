package br.com.enutri.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Paciente extends Usuario{

    @Column(nullable = false)
    private Nutricionista nutricionistaResponsavel;

    private String tipoSanguineo;

    private List<Relatorio> listaRelatorios;
}
