package br.com.enutri.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(scope = RegistroDiario.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RegistroDiario {

    public enum QualidadeSono {
        EXCELENTE,
        BOM,
        REGULAR,
        RUIM,
        PESSIMO,
        PENDENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plano_alimentar_id")
    private PlanoAlimentar planoAlimentar;

    private LocalDate data;

    @OneToMany
    private List<Refeicao> refeicoes;

    @ElementCollection
    private List<String> sintomas;

    @Enumerated(EnumType.STRING)
    private QualidadeSono qualidadeSono = QualidadeSono.PENDENTE;
}
