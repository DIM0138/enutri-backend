package br.com.enutri.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.enutri.util.randomTokenGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class TokenCadastro {
    
    @Id
    private String token;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    @JsonBackReference
    private Paciente paciente;

    private boolean usado;

    public TokenCadastro () {
        this.token = randomTokenGenerator.generateRandomString();
        this.usado = false;
    }

    public String newToken() {
        this.token = randomTokenGenerator.generateRandomString();

        return this.token;
    }

    public boolean isUsed() {
        return this.usado;
    }

}
