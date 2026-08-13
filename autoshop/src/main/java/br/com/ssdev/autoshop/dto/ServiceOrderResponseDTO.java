package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.OrderStatus;
import br.com.ssdev.autoshop.models.ServiceOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ServiceOrderResponseDTO(
        UUID id,
        Long orderNumber,
        UUID vehicleId,
        UUID mechanicId,
        UUID openedById,
        UUID closedById,
        String service,
        String description,
        LocalDateTime openingDate,
        LocalDateTime closingDate,
        OrderStatus status,
        BigDecimal finalValue
) {
    public ServiceOrderResponseDTO(ServiceOrder serviceOrder) {
        this(
                serviceOrder.getId(),
                serviceOrder.getOrderNumber(),
                serviceOrder.getVehicle() != null ? serviceOrder.getVehicle().getId() : null,
                serviceOrder.getMechanic() != null ? serviceOrder.getMechanic().getId() : null,
                serviceOrder.getOpenedBy() != null ? serviceOrder.getOpenedBy().getId() : null,
                serviceOrder.getClosedBy() != null ? serviceOrder.getClosedBy().getId() : null,
                serviceOrder.getService(),
                serviceOrder.getDescription(),
                serviceOrder.getOpeningDate(),
                serviceOrder.getClosingDate(),
                serviceOrder.getStatus(),
                serviceOrder.getFinalValue()
        );
    }
}
