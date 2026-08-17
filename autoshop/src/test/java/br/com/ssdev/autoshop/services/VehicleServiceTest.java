package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.VehicleRequestDTO;
import br.com.ssdev.autoshop.dto.VehicleResponseDTO;
import br.com.ssdev.autoshop.exceptions.CustomerNotFoundException;
import br.com.ssdev.autoshop.exceptions.VehicleNotFoundException;
import br.com.ssdev.autoshop.models.Address;
import br.com.ssdev.autoshop.models.Customer;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.models.UserRole;
import br.com.ssdev.autoshop.models.Vehicle;
import br.com.ssdev.autoshop.repositories.CustomerRepository;
import br.com.ssdev.autoshop.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;
    private Customer customer;
    private VehicleRequestDTO vehicleRequestDTO;
    private UUID vehicleId;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        vehicleId = UUID.randomUUID();
        customerId = UUID.randomUUID();

        User user = new User(UUID.randomUUID(), "Alice", "alice@example.com", "pass", UserRole.USER);
        Address address = new Address(UUID.randomUUID(), "Main", "City", "SP", "N", "00000-000", "1", null);
        customer = new Customer(customerId, user, address, "(11) 97777-6666");

        vehicle = new Vehicle(
                vehicleId,
                customer,
                "ABC1D23",
                "Civic",
                "Honda",
                "9BWZZZ377VT004251",
                2022
        );

        vehicleRequestDTO = new VehicleRequestDTO(
                vehicleId,
                customerId,
                "Honda",
                "Civic",
                "ABC1D23",
                "9BWZZZ377VT004251",
                2022
        );
    }

    @Test
    @DisplayName("Should create vehicle successfully when customer exists")
    void create_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleResponseDTO response = vehicleService.create(vehicleRequestDTO);

        assertNotNull(response);
        assertEquals("Honda", response.brand());
        assertEquals("Civic", response.model());
        assertEquals("ABC1D23", response.licensePlate());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer ID does not exist")
    void create_CustomerNotFoundException() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> vehicleService.create(vehicleRequestDTO));
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return vehicle when ID exists")
    void getById_Success() {
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

        VehicleResponseDTO response = vehicleService.getById(vehicleId);

        assertNotNull(response);
        assertEquals(vehicleId, response.id());
    }

    @Test
    @DisplayName("Should throw VehicleNotFoundException when vehicle ID does not exist")
    void getById_NotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(vehicleRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.getById(invalidId));
    }

    @Test
    @DisplayName("Should return paginated vehicles")
    void getAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Vehicle> page = new PageImpl<>(List.of(vehicle));
        when(vehicleRepository.findAll(pageable)).thenReturn(page);

        Page<VehicleResponseDTO> result = vehicleService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Should update vehicle successfully")
    void update_Success() {
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleResponseDTO response = vehicleService.update(vehicleId, vehicleRequestDTO);

        assertNotNull(response);
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    @DisplayName("Should delete vehicle successfully when ID exists")
    void deleteById_Success() {
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        doNothing().when(vehicleRepository).delete(vehicle);

        assertDoesNotThrow(() -> vehicleService.deleteById(vehicleId));
        verify(vehicleRepository, times(1)).delete(vehicle);
    }
}
