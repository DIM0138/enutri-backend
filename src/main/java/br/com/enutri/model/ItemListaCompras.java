package br.com.enutri.model;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class ItemListaCompras {
    private String ingrediente;
    private String metrica;
    private int quantidadeTotal;
}
