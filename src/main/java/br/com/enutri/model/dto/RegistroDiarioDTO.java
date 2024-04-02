package br.com.enutri.model.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class RegistroDiarioDTO {
    private long id;
    private LocalDate data;
    private List<RefeicaoDTO> refeicoes;
}
