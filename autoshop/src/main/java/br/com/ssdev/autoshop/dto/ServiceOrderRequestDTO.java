package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceOrderRequestDTO(
        UUID id,

        Long orderNumber,

        @NotNull(message = "The vehicle ID cannot be null")
        UUID vehicleId,

        @NotNull(message = "The mechanic ID cannot be null")
        UUID mechanicId,

        UUID openedById,

        UUID closedById,

        @NotBlank(message = "The service title cannot be empty")
        String service,

        @NotBlank(message = "The description cannot be empty")
        String description,

        OrderStatus status,

        @DecimalMin(value = "0.0", inclusive = true, message = "The final value must be zero or positive")
        BigDecimal finalValue
) {
}
