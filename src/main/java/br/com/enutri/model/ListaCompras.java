package br.com.enutri.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class ListaCompras {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    private Instant dataCriacao;

//    @Column(nullable = false)
//    private LocalDate dataInicio;
//
//    @Column(nullable = false)
//    private LocalDate dataFim;

//    @ElementCollection
//    @CollectionTable(name = "lista_compras_ingredientes", joinColumns = @JoinColumn(name = "lista_compras_id"))
//    @MapKeyColumn(name = "ingrediente")
//    @Column(name = "quantidade")
//    private HashMap<String, String> listaIngredientes;

    @ElementCollection
    private List<ItemListaCompras> itens;

}
