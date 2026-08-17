package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.CustomerRequestDTO;
import br.com.ssdev.autoshop.dto.CustomerResponseDTO;
import br.com.ssdev.autoshop.exceptions.AddressNotFoundException;
import br.com.ssdev.autoshop.exceptions.CustomerNotFoundException;
import br.com.ssdev.autoshop.exceptions.UserNotFoundException;
import br.com.ssdev.autoshop.models.Address;
import br.com.ssdev.autoshop.models.Customer;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.models.UserRole;
import br.com.ssdev.autoshop.repositories.AddressRepository;
import br.com.ssdev.autoshop.repositories.CustomerRepository;
import br.com.ssdev.autoshop.repositories.UserRepository;
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
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private User user;
    private Address address;
    private CustomerRequestDTO customerRequestDTO;
    private UUID customerId;
    private UUID userId;
    private UUID addressId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        userId = UUID.randomUUID();
        addressId = UUID.randomUUID();

        user = new User(userId, "Jane Doe", "jane.doe@example.com", "pass123", UserRole.USER);
        address = new Address(addressId, "Oak St", "CityX", "SP", "North", "12345-000", "50", null);
        customer = new Customer(customerId, user, address, "(11) 98888-7777");

        customerRequestDTO = new CustomerRequestDTO(addressId, "(11) 98888-7777", userId);
    }

    @Test
    @DisplayName("Should create customer successfully when user and address exist")
    void create_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponseDTO response = customerService.create(customerRequestDTO);

        assertNotNull(response);
        assertEquals(userId, response.userId());
        assertEquals(addressId, response.addressId());
        assertEquals("(11) 98888-7777", response.phone());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user does not exist in DB")
    void create_UserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> customerService.create(customerRequestDTO));
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw AddressNotFoundException when address does not exist in DB")
    void create_AddressNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> customerService.create(customerRequestDTO));
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return customer when ID exists")
    void getById_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerResponseDTO response = customerService.getById(customerId);

        assertNotNull(response);
        assertEquals(customerId, response.id());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer ID does not exist")
    void getById_NotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(customerRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getById(invalidId));
    }

    @Test
    @DisplayName("Should return paginated customers")
    void getAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of(customer));
        when(customerRepository.findAll(pageable)).thenReturn(page);

        Page<CustomerResponseDTO> result = customerService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Should update customer successfully")
    void update_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponseDTO response = customerService.update(customerId, customerRequestDTO);

        assertNotNull(response);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should delete customer successfully when ID exists")
    void deleteById_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        assertDoesNotThrow(() -> customerService.deleteById(customerId));
        verify(customerRepository, times(1)).delete(customer);
    }
}
