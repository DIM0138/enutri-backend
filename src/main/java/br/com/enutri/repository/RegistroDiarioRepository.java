package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.enutri.model.RegistroDiario;

@Repository
public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Long> {

}
