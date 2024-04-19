package br.com.enutri.repository;

import br.com.enutri.model.ListaCompras;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long> {
}
