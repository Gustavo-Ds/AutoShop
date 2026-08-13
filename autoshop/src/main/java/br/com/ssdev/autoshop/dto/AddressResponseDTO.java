package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.Address;

import java.util.UUID;

public record AddressResponseDTO(
        UUID id,
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        String number,
        String complement
) {
    public AddressResponseDTO(Address address){
        this(
                address.getId(),
                address.getCep(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getNumber(),
                address.getComplement()
        );
    }
}
