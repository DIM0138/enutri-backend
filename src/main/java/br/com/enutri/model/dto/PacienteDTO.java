package br.com.enutri.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.enutri.model.Paciente;
import br.com.enutri.model.Relatorio;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(scope = PacienteDTO.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
