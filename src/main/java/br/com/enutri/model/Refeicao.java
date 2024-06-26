package br.com.enutri.model;

import java.time.LocalTime;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(scope = Refeicao.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Refeicao {

    public enum Emocao {
        FELIZ,
        TRISTE,
        NEUTRO,
        ESTRESSADO,
        ANSIOSO,
        PENDENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receitaEscolhida;

    @Enumerated(EnumType.STRING)
    private Emocao emocao = Emocao.PENDENTE;

    private Boolean refeicaoFeita;
}
