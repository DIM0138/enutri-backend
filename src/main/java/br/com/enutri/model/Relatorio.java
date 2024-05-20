package br.com.enutri.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(scope = Relatorio.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionistaResponsavel;

    @Column(nullable = false)
    private LocalDate dataConsulta;

    @CollectionTable(name = "relatorio_medicoes")
    @OneToMany(cascade = CascadeType.ALL)
    private List<MedicaoRelatorio> medicoes;

    public Relatorio() {
        this.medicoes = new ArrayList<MedicaoRelatorio>();
    }
}
