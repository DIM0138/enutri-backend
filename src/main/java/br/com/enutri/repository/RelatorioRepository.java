package br.com.enutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.enutri.model.Relatorio;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

}
