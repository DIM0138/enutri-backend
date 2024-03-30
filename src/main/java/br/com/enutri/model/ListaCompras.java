package br.com.enutri.model;

import java.time.LocalDate;
import java.util.HashMap;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;

import lombok.Data;

@Data
@Entity
public class ListaCompras {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @ElementCollection
    @CollectionTable(name = "lista_compras_ingredientes", joinColumns = @JoinColumn(name = "lista_compras_id"))
    @MapKeyColumn(name = "ingrediente")
    @Column(name = "quantidade")
    private HashMap<String, String> listaIngredientes;

}
