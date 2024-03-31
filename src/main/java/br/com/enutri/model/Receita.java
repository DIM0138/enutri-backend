package br.com.enutri.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receita {

    public enum TipoRefeicao {
        CAFE,
        ALMOCO,
        JANTAR,
        LANCHE,
        OUTRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id", nullable = false)
    @JsonBackReference
    private Nutricionista nutricionista;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoRefeicao tipoRefeicao;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private int tempoPreparo;

    @Column(nullable = false)
    private int calorias;
    
    @Column(name = "imagem_url")
    private String imagemURL;

    @Column(nullable = false)
    @ElementCollection
    private List<String> modoPreparo;

    @ElementCollection
    @CollectionTable(name = "receita_ingredientes", joinColumns = @JoinColumn(name = "receita_id"))
    @MapKeyColumn(name = "ingrediente")
    @Column(name = "quantidade")
    private Map<String, String> listaIngredientes;

    @Column(nullable = false)
    private Boolean contemAlergicos;

    @ElementCollection
    private List<String> alergicos;
}
