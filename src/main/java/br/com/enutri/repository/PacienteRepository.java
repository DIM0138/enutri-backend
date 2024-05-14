package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.enutri.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente getReferenceByLogin(String login);
    Boolean existsByLogin(String login);
    Boolean existsByCPF(String cpf);
    Boolean existsByEmail(String email);
}
