package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.enutri.model.Medicao;

public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    Medicao findOneByNomeIgnoreCaseAndUnidadeIgnoreCase(String medicao, String unidade);
}
