package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.enutri.model.MedicaoRelatorio;

public interface MedicaoRelatorioRepository extends JpaRepository<MedicaoRelatorio, Long> {
    
}
