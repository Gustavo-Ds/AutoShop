package br.com.ssdev.autoshop.repository;

import br.com.ssdev.autoshop.model.OrcamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrcamentoRepository extends JpaRepository<OrcamentoModel, Long> {
}
