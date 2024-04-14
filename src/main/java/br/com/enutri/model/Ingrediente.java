package br.com.enutri.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ingrediente {

    public enum TipoMedida {
        QUILOS,
        GRAMAS,
        LITROS,
        MILILITROS,
        XICARAS,
        COLHER_DE_SOPA,
        COLHER_DE_CHA,
        UNIDADE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMedida medida;

    @PrePersist
    private void nomeUpperCase() {
        nome = nome.toUpperCase();
    }
}
