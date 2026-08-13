package br.com.ssdev.autoshop.repositories;

import br.com.ssdev.autoshop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
