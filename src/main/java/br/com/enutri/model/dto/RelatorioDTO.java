package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, String> dadosMedidos;

    public RelatorioDTO() {
        this.dadosMedidos = new HashMap<String, String>();
    }

    public RelatorioDTO(Relatorio relatorio) {
        this.id = relatorio.getId();
        this.paciente = relatorio.getPaciente().getId();
        this.nutricionistaResponsavel = relatorio.getNutricionistaResponsavel().getId();
        this.dataConsulta = relatorio.getDataConsulta();
        this.dadosMedidos = new HashMap<String, String>();
        this.dadosMedidos.putAll(relatorio.getDadosMedidos());
    }
}
