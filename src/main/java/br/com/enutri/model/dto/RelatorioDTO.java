package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.enutri.model.MedicaoRelatorio;
import br.com.enutri.model.Relatorio;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelatorioDTO {

    private long id;
    private long paciente;
    private long nutricionistaResponsavel;
    private LocalDate dataConsulta;
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
