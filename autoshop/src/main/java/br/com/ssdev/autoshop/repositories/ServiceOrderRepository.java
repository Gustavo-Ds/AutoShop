package br.com.ssdev.autoshop.repositories;

import br.com.ssdev.autoshop.models.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, UUID> {
    @Query("SELECT COALESCE(MAX(s.orderNumber), 0) + 1 FROM ServiceOrder s")
    Long findNextOrderNumber();
}
