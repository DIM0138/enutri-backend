package br.com.enutri.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column (nullable = false)
    private String nomeCompleto;

    @Column (nullable = false)
    private String dataNascimento;

    @Column (nullable = false)
    private String endereco;

    @Column (nullable = false)
    private String telefone;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false, unique = true)
    private String cpf;

    @Column (nullable = false, unique = true)
    private String login;

    @Column (nullable = false)
    private String senha;
}
