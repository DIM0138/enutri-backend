package br.com.enutri.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(scope = Receita.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @CollectionTable(name = "ingredientes_receita")
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<IngredienteReceita> ingredientes;

    @Column(nullable = false)
    private Boolean contemAlergicos;

    @ElementCollection
    private List<String> alergicos;
}
