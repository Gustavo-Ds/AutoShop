package br.com.ssdev.autoshop.repositories;

import br.com.ssdev.autoshop.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
