package br.com.enutri.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class PlanoAlimentar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonBackReference
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id", nullable = false)
    @JsonIgnore
    private Nutricionista nutricionistaResponsavel;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @OneToMany(mappedBy = "planoAlimentar", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Refeicao> listaRefeicoes;

    public List<Refeicao> getRefeicoesDiarias(LocalDate dataRefeicao) {
        List<Refeicao> refeicoesDoDia = new ArrayList<Refeicao>();

        for(Refeicao refeicao : this.getListaRefeicoes()) {
            if(refeicao.getDataRefeicao().equals(dataRefeicao)) {
                refeicoesDoDia.add(refeicao);
            }
        }
        
        return refeicoesDoDia;
    }
}
