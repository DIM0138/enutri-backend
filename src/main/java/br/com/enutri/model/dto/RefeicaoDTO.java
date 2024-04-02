package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.enutri.model.Receita;
import lombok.Data;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RefeicaoDTO {
    private long id;
    private LocalDate dataRefeicao;
    private LocalTime horario;
    private Receita receitaEscolhida;
}
