package br.com.enutri.model;

import java.time.Instant;
import java.util.List;
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
    
    @ElementCollection
    private List<ItemListaCompras> itens;

}
