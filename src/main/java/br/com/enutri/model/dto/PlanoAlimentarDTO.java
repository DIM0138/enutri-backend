package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.enutri.model.PlanoAlimentar;
import br.com.enutri.model.RegistroDiario;
import br.com.enutri.model.dto.validation.OnCreate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanoAlimentarDTO {

    private long id;

    @NotNull(message = "O paciente deve ser informado.", groups = OnCreate.class)
    private PacienteDTO paciente;

    @NotNull(message = "O ID do nutricionista responsável deve ser informado.", groups = OnCreate.class)
    private long nutricionistaResponsavel;

    @NotNull(message = "A data inicial deve ser informada.", groups = OnCreate.class)
    private LocalDate dataInicio;

    @NotNull(message = "A data final deve ser informada.", groups = OnCreate.class)
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
