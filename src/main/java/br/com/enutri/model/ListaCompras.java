package br.com.enutri.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListaCompras {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private Date dataInicio;

    @Column(nullable = false)
    private Date dataFim;
    
    @Column(nullable = false)
    private HashMap<Ingrediente, Integer> listaIngredientes;

}
