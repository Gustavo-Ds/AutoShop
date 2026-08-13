package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.Vehicle;

import java.util.UUID;

public record VehicleResponseDTO(
        UUID id,
        String brand,
        String model,
        String chassis,
        String licensePlate,
        Integer year
) {
    public VehicleResponseDTO(Vehicle vehicle) {
        this(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getChassis(),
                vehicle.getLicensePlate(),
                vehicle.getYear()
        );
    }
}
