package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.enutri.model.MedicaoRelatorio;
import br.com.enutri.model.Relatorio;
import br.com.enutri.model.dto.validation.OnCreate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelatorioDTO {

    private long id;

    @NotNull(message = "O ID do paciente deve ser informado.", groups = OnCreate.class)
    private long paciente;

    @NotNull(message = "O ID do nutricionista responsável deve ser informado.", groups = OnCreate.class)
    private long nutricionistaResponsavel;

    @NotNull(message = "A data da consulta deve ser informada.", groups = OnCreate.class)
    private LocalDate dataConsulta;

    @NotEmpty(message = "Pelo menos uma medicão deve ser informada.", groups = OnCreate.class)
    private List<MedicaoRelatorio> medicoes;

    public RelatorioDTO() {
        this.medicoes = new ArrayList<MedicaoRelatorio>();
    }

    public RelatorioDTO(Relatorio relatorio) {
        this.id = relatorio.getId();
        this.paciente = relatorio.getPaciente().getId();
        this.nutricionistaResponsavel = relatorio.getNutricionistaResponsavel().getId();
        this.dataConsulta = relatorio.getDataConsulta();
        this.medicoes = relatorio.getMedicoes();
    }
}
