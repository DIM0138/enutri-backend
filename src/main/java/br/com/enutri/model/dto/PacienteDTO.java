package br.com.enutri.model.dto;

import java.util.List;

import br.com.enutri.model.Paciente;
import br.com.enutri.model.Relatorio;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PacienteDTO extends UsuarioDTO{
    private long nutricionistaResponsavelId;
    private List<Long> listaRelatoriosIds;
    private String token;

    public PacienteDTO() {}

    public PacienteDTO(Paciente paciente) {
        super(paciente);
        this.nutricionistaResponsavelId = paciente.getNutricionistaResponsavel().getId();
        this.listaRelatoriosIds = paciente.getListaRelatorios().stream().map(Relatorio::getId).toList();
        this.token = paciente.getToken().getToken();
    }
}
