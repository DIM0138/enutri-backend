package br.com.enutri.model.dto;

import br.com.enutri.model.TokenCadastro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCadastroDTO {
    private String token;
    private long idPaciente;
    private String nomePaciente;
    private long idNutricionista;
    private String nomeNutricionista;
    private Boolean usado;

    public TokenCadastroDTO(TokenCadastro token) {
        this.token = token.getToken();
        this.idPaciente = token.getPaciente().getId();
        this.nomePaciente = token.getPaciente().getNomeCompleto();
        this.idNutricionista = token.getPaciente().getNutricionistaResponsavel().getId();
        this.nomeNutricionista = token.getPaciente().getNutricionistaResponsavel().getNomeCompleto();
        this.usado = token.isUsed();
    }
}
