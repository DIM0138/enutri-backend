package br.com.enutri.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "plano_alimentar_id")
    @JsonBackReference
    private PlanoAlimentar planoAlimentar;

    @Column(nullable = false)
    private LocalDate dataRefeicao;

    @Column(nullable = false)
    private LocalTime horario;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receitaEscolhida;

}
