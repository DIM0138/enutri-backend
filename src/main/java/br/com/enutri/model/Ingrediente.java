package br.com.enutri.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Ingrediente {

    private enum TipoMedida {
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
}
