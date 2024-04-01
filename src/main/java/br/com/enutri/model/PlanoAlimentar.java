package br.com.enutri.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PlanoAlimentar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id", nullable = false)
    private Nutricionista nutricionistaResponsavel;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @OneToMany(mappedBy = "planoAlimentar", cascade = CascadeType.ALL)
    private List<RegistroDiario> registrosDiarios;

    public RegistroDiario getByDia(LocalDate data) {
        for (RegistroDiario registroDiario : this.registrosDiarios) {
            if (registroDiario.getData().equals(data)) {
                return registroDiario;
            }
        }
        return null;
    }
}
