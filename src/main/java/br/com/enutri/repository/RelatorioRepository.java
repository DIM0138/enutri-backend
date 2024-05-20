package br.com.enutri.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.enutri.model.Relatorio;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    List<Relatorio> findByPacienteId(Long id);
    Boolean existsByDataConsultaAndPacienteId(LocalDate data, Long id);
}
