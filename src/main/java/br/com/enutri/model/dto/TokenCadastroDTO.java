package br.com.enutri.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.enutri.model.TokenCadastro;
import lombok.Data;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "token")
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
