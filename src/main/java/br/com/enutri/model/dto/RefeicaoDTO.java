package br.com.enutri.model.dto;

import java.time.LocalTime;

import br.com.enutri.model.Refeicao;
import br.com.enutri.model.Refeicao.Emocao;
import br.com.enutri.model.dto.validation.OnCreate;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefeicaoDTO {

    private long id;

    @NotNull(message = "A data deve ser informada.", groups = OnCreate.class)
    private LocalDate data;

    @NotNull(message = "O horário deve ser informado.", groups = OnCreate.class)
    private LocalTime horario;

    @NotNull(message = "A receita deve ser informada.", groups = OnCreate.class)
    private ReceitaDTO receitaEscolhida;

    private Emocao emocao = Emocao.PENDENTE;

    private Boolean refeicaoFeita = false;

    public RefeicaoDTO(Refeicao refeicao) {
        this.id = refeicao.getId();
        this.data = refeicao.getData();
        this.horario = refeicao.getHorario();
        this.receitaEscolhida = new ReceitaDTO(refeicao.getReceitaEscolhida());
        this.emocao = refeicao.getEmocao();
        this.refeicaoFeita = refeicao.getRefeicaoFeita();
    }
}
