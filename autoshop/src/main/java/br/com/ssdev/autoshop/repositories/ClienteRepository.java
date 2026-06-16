package br.com.ssdev.autoshop.repositories;

import br.com.ssdev.autoshop.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
}
