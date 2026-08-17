package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.ServiceOrderRequestDTO;
import br.com.ssdev.autoshop.dto.ServiceOrderResponseDTO;
import br.com.ssdev.autoshop.exceptions.ServiceOrderNotFoundException;
import br.com.ssdev.autoshop.exceptions.UserNotFoundException;
import br.com.ssdev.autoshop.exceptions.VehicleNotFoundException;
import br.com.ssdev.autoshop.models.Address;
import br.com.ssdev.autoshop.models.Customer;
import br.com.ssdev.autoshop.models.OrderStatus;
import br.com.ssdev.autoshop.models.ServiceOrder;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.models.UserRole;
import br.com.ssdev.autoshop.models.Vehicle;
import br.com.ssdev.autoshop.repositories.ServiceOrderRepository;
import br.com.ssdev.autoshop.repositories.UserRepository;
import br.com.ssdev.autoshop.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceOrderServiceTest {

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ServiceOrderService serviceOrderService;

    private ServiceOrder serviceOrder;
    private Vehicle vehicle;
    private User mechanic;
    private ServiceOrderRequestDTO serviceOrderRequestDTO;
    private UUID orderId;
    private UUID vehicleId;
    private UUID mechanicId;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        vehicleId = UUID.randomUUID();
        mechanicId = UUID.randomUUID();

        User owner = new User(UUID.randomUUID(), "Owner", "owner@example.com", "pass", UserRole.USER);
        Address address = new Address(UUID.randomUUID(), "St", "City", "SP", "N", "00000-000", "1", null);
        Customer customer = new Customer(UUID.randomUUID(), owner, address, "(11) 90000-0000");

        vehicle = new Vehicle(vehicleId, customer, "XYZ9876", "Corolla", "Toyota", "9BWZZZ377VT999999", 2021);
        mechanic = new User(mechanicId, "Bob Mechanic", "bob@example.com", "pass", UserRole.MECHANIC);

        serviceOrder = new ServiceOrder(
                orderId,
                101L,
                vehicle,
                mechanic,
                null,
                null,
                "Oil Change",
                "Full synthetic oil change",
                LocalDateTime.now(),
                null,
                OrderStatus.OPEN,
                new BigDecimal("250.00")
        );

        serviceOrderRequestDTO = new ServiceOrderRequestDTO(
                orderId,
                null,
                vehicleId,
                mechanicId,
                null,
                null,
                "Oil Change",
                "Full synthetic oil change",
                OrderStatus.OPEN,
                new BigDecimal("250.00")
        );
    }

    @Test
    @DisplayName("Should create service order and auto-generate order number sequentially")
    void create_Success_GeneratesOrderNumberSequentially() {
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(userRepository.findById(mechanicId)).thenReturn(Optional.of(mechanic));
        when(serviceOrderRepository.findNextOrderNumber()).thenReturn(101L);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        ServiceOrderResponseDTO response = serviceOrderService.create(serviceOrderRequestDTO);

        assertNotNull(response);
        assertEquals(101L, response.orderNumber());
        assertEquals("Oil Change", response.service());
        verify(serviceOrderRepository, times(1)).findNextOrderNumber();
        verify(serviceOrderRepository, times(1)).save(any(ServiceOrder.class));
    }

    @Test
    @DisplayName("Should throw VehicleNotFoundException when vehicle ID does not exist")
    void create_VehicleNotFoundException() {
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> serviceOrderService.create(serviceOrderRequestDTO));
        verify(serviceOrderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when mechanic ID does not exist")
    void create_MechanicUserNotFoundException() {
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(userRepository.findById(mechanicId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> serviceOrderService.create(serviceOrderRequestDTO));
        verify(serviceOrderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return service order when ID exists")
    void getById_Success() {
        when(serviceOrderRepository.findById(orderId)).thenReturn(Optional.of(serviceOrder));

        ServiceOrderResponseDTO response = serviceOrderService.getById(orderId);

        assertNotNull(response);
        assertEquals(orderId, response.id());
    }

    @Test
    @DisplayName("Should throw ServiceOrderNotFoundException when ID does not exist")
    void getById_NotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(serviceOrderRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ServiceOrderNotFoundException.class, () -> serviceOrderService.getById(invalidId));
    }

    @Test
    @DisplayName("Should set closing date automatically when updating status to FINISHED")
    void update_Success_SetsClosingDateWhenFinished() {
        ServiceOrderRequestDTO finishedRequest = new ServiceOrderRequestDTO(
                orderId,
                101L,
                vehicleId,
                mechanicId,
                null,
                null,
                "Oil Change",
                "Done",
                OrderStatus.FINISHED,
                new BigDecimal("250.00")
        );

        when(serviceOrderRepository.findById(orderId)).thenReturn(Optional.of(serviceOrder));
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(userRepository.findById(mechanicId)).thenReturn(Optional.of(mechanic));
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        ServiceOrderResponseDTO response = serviceOrderService.update(orderId, finishedRequest);

        assertNotNull(response);
        assertEquals(OrderStatus.FINISHED, serviceOrder.getStatus());
        assertNotNull(serviceOrder.getClosingDate());
        verify(serviceOrderRepository, times(1)).save(any(ServiceOrder.class));
    }

    @Test
    @DisplayName("Should delete service order when ID exists")
    void deleteById_Success() {
        when(serviceOrderRepository.findById(orderId)).thenReturn(Optional.of(serviceOrder));
        doNothing().when(serviceOrderRepository).delete(serviceOrder);

        assertDoesNotThrow(() -> serviceOrderService.deleteById(orderId));
        verify(serviceOrderRepository, times(1)).delete(serviceOrder);
    }
}
