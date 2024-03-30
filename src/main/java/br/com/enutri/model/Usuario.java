package br.com.enutri.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false)
    private String nomeCompleto;
    private String genero;

    private LocalDate dataNascimento;
    private String endereco;
    private String telefone;
    private String email;
    private String CPF;
    private String login;
    private String senha;

    public Usuario() {
        this.nomeCompleto = "";
        this.genero = "";
        this.endereco = "";
        this.telefone = "";
        this.email = "";
        this.CPF = "";
    }
}
