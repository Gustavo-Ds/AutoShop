package br.com.ssdev.autoshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record VehicleRequestDTO(
        UUID id,

        @NotNull(message = "The customer ID cannot be null")
        UUID customerId,

        @NotBlank(message = "The brand cannot be empty")
        String brand,

        @NotBlank(message = "The model cannot be empty")
        String model,

        @NotBlank(message = "The license plate cannot be empty")
        @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}", message = "The license plate must be valid (e.g. ABC1D23 or ABC1234)")
        String licensePlate,

        @NotBlank(message = "The chassis number cannot be empty")
        String chassis,

        @NotNull(message = "The year cannot be null")
        @Min(value = 1900, message = "The year must be greater than or equal to 1900")
        Integer year
) {
}
