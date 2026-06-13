package br.com.ssdev.autoshop.repository;

import br.com.ssdev.autoshop.model.OrdemServicoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemServicoRepository extends JpaRepository<OrdemServicoModel, Long> {
}
