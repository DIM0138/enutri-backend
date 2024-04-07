package br.com.enutri.model;

import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(scope = Usuario.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Paciente extends Usuario{

    @OneToOne
    @JoinColumn(name = "token_id")
    private TokenCadastro token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionistaResponsavel;

    @OneToMany(mappedBy = "paciente")
    private List<Relatorio> listaRelatorios;

    @OneToMany(mappedBy = "paciente")
    private List<PlanoAlimentar> planosAlimentares;

    @OneToOne
    @JoinColumn(name = "plano_alimentar_atual_id")
    private PlanoAlimentar planoAtual;

    public void addRelatorio(Relatorio relatorio) {
        this.listaRelatorios.add(relatorio);
    }

    public void addPlanoAlimentar(PlanoAlimentar planoAlimentar) {
        this.planosAlimentares.add(planoAlimentar);
    }

    public PlanoAlimentar getPlanoAlimentarAtual() throws NoSuchElementException {
        for (PlanoAlimentar planoAlimentar : this.planosAlimentares) {
            if (planoAlimentar.getAtivo() == true) {
                return planoAlimentar;
            }
        } 
        
        throw new NoSuchElementException("Nenhum plano alimentar ativo encontrado");
    }
}
