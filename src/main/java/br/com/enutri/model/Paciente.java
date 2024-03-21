package br.com.enutri.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Paciente extends Usuario{

    private int altura;
    private int peso;
    private float imc;
}
