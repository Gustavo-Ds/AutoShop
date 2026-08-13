package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.VehicleRequestDTO;
import br.com.ssdev.autoshop.dto.VehicleResponseDTO;
import br.com.ssdev.autoshop.exceptions.CustomerNotFoundException;
import br.com.ssdev.autoshop.exceptions.VehicleNotFoundException;
import br.com.ssdev.autoshop.models.Customer;
import br.com.ssdev.autoshop.models.Vehicle;
import br.com.ssdev.autoshop.repositories.CustomerRepository;
import br.com.ssdev.autoshop.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String MESSAGE = "Vehicle not found";

    public VehicleResponseDTO create(VehicleRequestDTO vehicleRequestDTO) {
        Customer customer = customerRepository.findById(vehicleRequestDTO.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setCustomer(customer);
        vehicle.setBrand(vehicleRequestDTO.brand());
        vehicle.setModel(vehicleRequestDTO.model());
        vehicle.setLicensePlate(vehicleRequestDTO.licensePlate());
        vehicle.setChassis(vehicleRequestDTO.chassis());
        vehicle.setYear(vehicleRequestDTO.year());

        vehicleRepository.save(vehicle);
        return new VehicleResponseDTO(vehicle);
    }

    public Page<VehicleResponseDTO> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable).map(VehicleResponseDTO::new);
    }

    public VehicleResponseDTO getById(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(MESSAGE));
        return new VehicleResponseDTO(vehicle);
    }

    public VehicleResponseDTO update(UUID id, VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(MESSAGE));

        Customer customer = customerRepository.findById(vehicleRequestDTO.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        vehicle.setCustomer(customer);
        vehicle.setBrand(vehicleRequestDTO.brand());
        vehicle.setModel(vehicleRequestDTO.model());
        vehicle.setLicensePlate(vehicleRequestDTO.licensePlate());
        vehicle.setChassis(vehicleRequestDTO.chassis());
        vehicle.setYear(vehicleRequestDTO.year());

        vehicleRepository.save(vehicle);
        return new VehicleResponseDTO(vehicle);
    }

    public void deleteById(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(MESSAGE));
        vehicleRepository.delete(vehicle);
    }
}
