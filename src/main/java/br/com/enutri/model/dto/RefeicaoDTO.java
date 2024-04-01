package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.enutri.model.Receita;
import lombok.Data;

@Data
public class RefeicaoDTO {
    private long id;
    private LocalDate dataRefeicao;
    private LocalTime horario;
    private Receita receitaEscolhida;
}
