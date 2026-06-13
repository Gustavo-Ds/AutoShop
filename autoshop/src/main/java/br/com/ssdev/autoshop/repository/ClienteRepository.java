package br.com.ssdev.autoshop.repository;

import br.com.ssdev.autoshop.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
}
