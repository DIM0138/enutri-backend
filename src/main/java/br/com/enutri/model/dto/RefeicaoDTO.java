package br.com.enutri.model.dto;

import java.time.LocalTime;

import br.com.enutri.model.Refeicao;
import br.com.enutri.model.Refeicao.Emocao;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefeicaoDTO {

    private long id;
    private LocalDate data;
    private LocalTime horario;
    private long receitaEscolhida;
    private Emocao emocao = Emocao.PENDENTE;
    private Boolean refeicaoFeita = false;

    public RefeicaoDTO(Refeicao refeicao) {
        this.data = refeicao.getData();
        this.horario = refeicao.getHorario();
        this.receitaEscolhida = refeicao.getReceitaEscolhida().getId();
        this.emocao = refeicao.getEmocao();
        this.refeicaoFeita = refeicao.getRefeicaoFeita();
    }
}
