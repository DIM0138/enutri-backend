package br.com.enutri.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(scope = PlanoAlimentar.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PlanoAlimentar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionistaResponsavel;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    @OneToMany(mappedBy = "planoAlimentar", cascade = CascadeType.ALL)
    private List<RegistroDiario> registrosDiarios;

    private Boolean ativo = false;

    public RegistroDiario getRegistroDiarioByDate(LocalDate data) {
        for (RegistroDiario registroDiario : this.registrosDiarios) {
            if (registroDiario.getData().equals(data)) {
                return registroDiario;
            }
        }
        return null;
    }

    public void addRegistroDiario(RegistroDiario registroDiario) {
        this.registrosDiarios.add(registroDiario);
    }
}
