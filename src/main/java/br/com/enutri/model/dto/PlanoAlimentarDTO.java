package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import lombok.Data;

@Data
public class PlanoAlimentarDTO {
    private Long id;
    private Paciente paciente;
    private Nutricionista nutricionistaResponsavel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<RefeicaoDTO> listaRefeicoes;
}
