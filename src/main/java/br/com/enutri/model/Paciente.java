package br.com.enutri.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Paciente extends Usuario{

    @OneToOne
    @JoinColumn(name = "token_id")
    @JsonManagedReference
    private TokenCadastro token;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    @JsonBackReference
    private Nutricionista nutricionistaResponsavel;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference
    private List<Relatorio> listaRelatorios;

    @OneToOne(mappedBy = "paciente")
    @JoinColumn(name = "plano_alimentar_id")
    @JsonManagedReference
    private PlanoAlimentar planoAtual;
}
