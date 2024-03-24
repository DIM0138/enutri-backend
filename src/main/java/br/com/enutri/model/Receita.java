package br.com.enutri.model;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Receita {

    private enum TipoRefeicao {
        CAFE,
        ALMOCO,
        JANTAR,
        LANCHE,
        OUTRO
    }

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private Nutricionista nutricionista;

    @Column(nullable = false)
    private TipoRefeicao tipoRefeicao;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private int tempoPreparo;
    
    private String imagemURL;

    @Column(nullable = false)
    private List<String> modoPreparo;

    @Column(nullable = false)
    private HashMap<Ingrediente, Integer> listaIngredientes;

    @Column(nullable = false)
    private Boolean contemAlergicos;

    private List<String> alergicos;
}
