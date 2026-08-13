package br.com.ssdev.autoshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserRequestDTO(
        UUID id,

        @NotBlank(message = "The name cannot be empty")
        @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "The email cannot be empty")
        @Email(message = "The email format is invalid")
        String email,

        @NotBlank(message = "The password cannot be empty")
        @Size(min = 6, message = "The password must be at least 6 characters long")
        String password,

        @NotBlank(message = "The role cannot be empty")
        String role
) {
}
