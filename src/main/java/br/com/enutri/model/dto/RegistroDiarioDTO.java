package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.enutri.model.Refeicao;
import br.com.enutri.model.RegistroDiario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistroDiarioDTO {
    private long id;
    private long PlanoAlimentar;
    private LocalDate data;
    private List<RefeicaoDTO> refeicoes;

    public RegistroDiarioDTO() {
        this.refeicoes = new ArrayList<RefeicaoDTO>();
    }

    public RegistroDiarioDTO(RegistroDiario registroDiario) {
        this.id = registroDiario.getId();
        this.PlanoAlimentar = registroDiario.getPlanoAlimentar().getId();
        this.data = registroDiario.getData();
        this.refeicoes = new ArrayList<RefeicaoDTO>();
        
        for(Refeicao refeicao : registroDiario.getRefeicoes()) {
            this.refeicoes.add(new RefeicaoDTO(refeicao));
        }
    }
}
