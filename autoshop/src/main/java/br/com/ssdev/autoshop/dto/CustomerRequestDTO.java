package br.com.ssdev.autoshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CustomerRequestDTO(
        @NotNull(message = "The address ID cannot be null")
        UUID addressId,

        @NotBlank(message = "The phone number cannot be empty")
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "The phone number must be valid (e.g., (11) 99999-9999 or 11999999999)")
        String phone,

        @NotNull(message = "The user ID cannot be null")
        UUID userId
) {
}
