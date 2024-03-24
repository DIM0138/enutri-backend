package br.com.enutri.model;

import java.util.Date;
import java.util.UUID;
import java.util.List;
import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PlanoAlimentar {
    
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private Nutricionista nutricionista;

    @Column(nullable = false)
    private Date dataInicio;

    @Column(nullable = false)
    private Date dataFim;

    @Column(nullable = false)
    private HashMap<Date, List<Refeicao>> planejamentoDiario;
}
