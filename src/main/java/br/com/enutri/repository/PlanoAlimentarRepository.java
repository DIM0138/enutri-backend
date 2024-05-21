package br.com.enutri.repository;

import java.util.List;

import br.com.enutri.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.enutri.model.Nutricionista;
import br.com.enutri.model.PlanoAlimentar;

@Repository
public interface PlanoAlimentarRepository extends JpaRepository<PlanoAlimentar, Long> {
    public List<PlanoAlimentar> getByNutricionistaResponsavel(Nutricionista nutricionista);
    public List<PlanoAlimentar> getByPaciente(Paciente paciente);
}
