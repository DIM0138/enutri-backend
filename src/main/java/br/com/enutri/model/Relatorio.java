package br.com.enutri.model;

import java.time.LocalDate;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import lombok.Data;

@Data
@Entity
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @JsonBackReference
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionistaResponsavel;

    @Column(nullable = false)
    private LocalDate dataConsulta;

    @ElementCollection
    @CollectionTable(name = "relatorio_dados_medidos", joinColumns = @JoinColumn(name = "relatorio_id"))
    @MapKeyColumn(name = "medicao")
    @Column(name = "resultado")
    private HashMap<String,String> dadosMedidos;
}
