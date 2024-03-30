package br.com.enutri.model.dto;

import java.util.List;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NutricionistaDTO extends UsuarioDTO{
    private String CRN;
    private String formacao;
    private String especialidade;
    private String enderecoProfissional;
    private List<Long> listaPacientesIds;

    public NutricionistaDTO() {}
    
    public NutricionistaDTO(Nutricionista nutricionistaConsultado) {
        super(nutricionistaConsultado);
        this.CRN = nutricionistaConsultado.getCRN();
        this.formacao = nutricionistaConsultado.getFormacao();
        this.especialidade = nutricionistaConsultado.getEspecialidade();
        this.enderecoProfissional = nutricionistaConsultado.getEnderecoProfissional();
        this.listaPacientesIds = nutricionistaConsultado.getListaPacientes().stream().map(Paciente::getId).toList();
    }
}
