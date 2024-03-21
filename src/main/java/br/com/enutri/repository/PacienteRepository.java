package br.com.enutri.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.enutri.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

}
