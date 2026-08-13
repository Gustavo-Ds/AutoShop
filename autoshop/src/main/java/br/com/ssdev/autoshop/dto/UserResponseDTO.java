package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.User;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String role
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().getRole() : null
        );
    }
}
