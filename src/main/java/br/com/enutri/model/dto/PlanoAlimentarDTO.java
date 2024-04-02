package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.Paciente;
import lombok.Data;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PlanoAlimentarDTO {
    private long id;
    private Paciente paciente;
    private Nutricionista nutricionistaResponsavel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<RegistroDiarioDTO> registrosDiarios;
}
