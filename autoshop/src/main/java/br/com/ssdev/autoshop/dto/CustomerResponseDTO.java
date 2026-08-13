package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.Customer;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        UUID userId,
        UUID addressId,
        String phone
) {
    public CustomerResponseDTO(Customer customer) {
        this(
                customer.getId(),
                customer.getUser() != null ? customer.getUser().getId() : null,
                customer.getAddress() != null ? customer.getAddress().getId() : null,
                customer.getPhone()
        );
    }
}
