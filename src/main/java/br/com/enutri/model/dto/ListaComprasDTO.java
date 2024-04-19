package br.com.enutri.model.dto;

import br.com.enutri.model.ItemListaCompras;
import br.com.enutri.model.ListaCompras;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ListaComprasDTO {

    private long id;
    private Instant dataCriacao;
    private List<ItemListaCompras> itens;

    public ListaComprasDTO(ListaCompras listaCompras){
        this.id = listaCompras.getId();
        this.dataCriacao = listaCompras.getDataCriacao();
        this.itens = listaCompras.getItens();
    }
}
