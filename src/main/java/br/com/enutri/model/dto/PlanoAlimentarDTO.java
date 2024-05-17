package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.RegistroDiario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanoAlimentarDTO {
    private long id;
    private PacienteDTO paciente;
    private long nutricionistaResponsavel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativo = false;
    private List<RegistroDiarioDTO> registrosDiarios;

    public PlanoAlimentarDTO() {
        this.registrosDiarios = new ArrayList<RegistroDiarioDTO>();
    }

    public PlanoAlimentarDTO(PlanoAlimentar planoAlimentar) {
        this.id = planoAlimentar.getId();
        this.paciente = new PacienteDTO(planoAlimentar.getPaciente());
        this.nutricionistaResponsavel = planoAlimentar.getNutricionistaResponsavel().getId();
        this.dataInicio = planoAlimentar.getDataInicio();
        this.dataFim = planoAlimentar.getDataFim();
        this.ativo = planoAlimentar.getAtivo();
        this.registrosDiarios = new ArrayList<RegistroDiarioDTO>();
        
        for(RegistroDiario registroDiario : planoAlimentar.getRegistrosDiarios()) {
            this.registrosDiarios.add(new RegistroDiarioDTO(registroDiario));
        }
    }
}
