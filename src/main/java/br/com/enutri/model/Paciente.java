package br.com.enutri.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.enutri.exception.ResourceNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(scope = Usuario.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Paciente extends Usuario{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id")
    private TokenCadastro token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionistaResponsavel;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Relatorio> listaRelatorios;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
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

    public PlanoAlimentar getPlanoAlimentarAtual() throws ResourceNotFoundException {
        for (PlanoAlimentar planoAlimentar : this.planosAlimentares) {
            if (planoAlimentar.getAtivo() == true) {
                return planoAlimentar;
            }
        } 
        
        throw new ResourceNotFoundException("Nenhum plano alimentar ativo encontrado");
    }

    public Boolean existsPlanoAlimentarAtivo() {
        for (PlanoAlimentar planoAlimentar : this.planosAlimentares) {
            if (planoAlimentar.getAtivo() == true) {
                return true;
            }
        }
        return false;
    }
}
