package br.com.enutri.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Paciente extends Usuario{

    @OneToOne
    @JoinColumn(name = "token_id")
    private TokenCadastro token;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionistaResponsavel;

    @OneToMany(mappedBy = "paciente")
    private List<Relatorio> listaRelatorios;

    @OneToOne
    @JoinColumn(name = "plano_alimentar_id")
    private PlanoAlimentar planoAtual;
}
