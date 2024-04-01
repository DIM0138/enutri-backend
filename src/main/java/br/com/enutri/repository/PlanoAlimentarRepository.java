package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.enutri.model.PlanoAlimentar;

@Repository
public interface PlanoAlimentarRepository extends JpaRepository<PlanoAlimentar, Long> {

}
