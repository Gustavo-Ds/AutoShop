package br.com.ssdev.autoshop.repositories;

import br.com.ssdev.autoshop.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
}
