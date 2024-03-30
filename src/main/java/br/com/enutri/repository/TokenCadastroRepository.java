package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.enutri.model.TokenCadastro;

@Repository
public interface TokenCadastroRepository extends JpaRepository<TokenCadastro, String> {

}
