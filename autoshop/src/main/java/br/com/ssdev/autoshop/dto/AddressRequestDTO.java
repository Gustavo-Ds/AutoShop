package br.com.ssdev.autoshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record AddressRequestDTO(
        UUID id,
        @NotBlank(message = "CEP cannot be empty")
        @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "CEP must be in format 00000-000 or 8 digits")
        String cep,

        @NotBlank(message = "Street cannot be empty")
        String street,

        @NotBlank(message = "Neighborhood cannot be empty")
        String neighborhood,

        @NotBlank(message = "City cannot be empty")
        String city,

        @NotBlank(message = "State cannot be empty")
        String state,

        @NotBlank(message = "Number cannot be empty")
        String number,

        String complement
) {
}
