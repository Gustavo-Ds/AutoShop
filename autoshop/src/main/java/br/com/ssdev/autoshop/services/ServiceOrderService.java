package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.ServiceOrderRequestDTO;
import br.com.ssdev.autoshop.dto.ServiceOrderResponseDTO;
import br.com.ssdev.autoshop.exceptions.ServiceOrderNotFoundException;
import br.com.ssdev.autoshop.exceptions.UserNotFoundException;
import br.com.ssdev.autoshop.exceptions.VehicleNotFoundException;
import br.com.ssdev.autoshop.models.OrderStatus;
import br.com.ssdev.autoshop.models.ServiceOrder;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.models.Vehicle;
import br.com.ssdev.autoshop.repositories.ServiceOrderRepository;
import br.com.ssdev.autoshop.repositories.UserRepository;
import br.com.ssdev.autoshop.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String MESSAGE = "Service order not found";

    public ServiceOrderResponseDTO create(ServiceOrderRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        User mechanic = userRepository.findById(dto.mechanicId())
                .orElseThrow(() -> new UserNotFoundException("Mechanic user not found"));

        ServiceOrder order = new ServiceOrder();
        order.setOrderNumber(serviceOrderRepository.findNextOrderNumber());
        order.setVehicle(vehicle);
        order.setMechanic(mechanic);
        order.setService(dto.service());
        order.setDescription(dto.description());
        order.setOpeningDate(LocalDateTime.now());
        order.setStatus(dto.status() != null ? dto.status() : OrderStatus.OPEN);
        order.setFinalValue(dto.finalValue());

        if (dto.openedById() != null) {
            User openedBy = userRepository.findById(dto.openedById()).orElse(null);
            order.setOpenedBy(openedBy);
        }

        serviceOrderRepository.save(order);
        return new ServiceOrderResponseDTO(order);
    }

    public Page<ServiceOrderResponseDTO> getAll(Pageable pageable) {
        return serviceOrderRepository.findAll(pageable).map(ServiceOrderResponseDTO::new);
    }

    public ServiceOrderResponseDTO getById(UUID id) {
        ServiceOrder order = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(MESSAGE));
        return new ServiceOrderResponseDTO(order);
    }

    public ServiceOrderResponseDTO update(UUID id, ServiceOrderRequestDTO dto) {
        ServiceOrder order = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(MESSAGE));

        Vehicle vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        User mechanic = userRepository.findById(dto.mechanicId())
                .orElseThrow(() -> new UserNotFoundException("Mechanic user not found"));

        order.setVehicle(vehicle);
        order.setMechanic(mechanic);
        order.setService(dto.service());
        order.setDescription(dto.description());
        if (dto.status() != null) {
            order.setStatus(dto.status());
            if (dto.status() == OrderStatus.FINISHED && order.getClosingDate() == null) {
                order.setClosingDate(LocalDateTime.now());
            }
        }
        order.setFinalValue(dto.finalValue());

        if (dto.closedById() != null) {
            User closedBy = userRepository.findById(dto.closedById()).orElse(null);
            order.setClosedBy(closedBy);
        }

        serviceOrderRepository.save(order);
        return new ServiceOrderResponseDTO(order);
    }

    public void deleteById(UUID id) {
        ServiceOrder order = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(MESSAGE));
        serviceOrderRepository.delete(order);
    }
}
