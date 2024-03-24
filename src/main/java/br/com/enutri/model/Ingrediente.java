package br.com.enutri.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private TipoMedida medida;
    
    @Column(nullable = false)
    private Boolean contemAlergicos;
    
    private List<String> alergicos;
}
