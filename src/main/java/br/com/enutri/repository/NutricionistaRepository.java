package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.enutri.model.Nutricionista;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long>{
    Boolean existsByLogin(String login);
    Boolean existsByCPF(String cpf);
    Boolean existsByEmail(String email);
    Boolean existsByCRN(String crn);
    Nutricionista getReferenceByLogin(String login);
}
