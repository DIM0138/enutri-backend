package br.com.enutri.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(scope = NutricionistaDTO.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
