package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.AddressRequestDTO;
import br.com.ssdev.autoshop.dto.AddressResponseDTO;
import br.com.ssdev.autoshop.exceptions.AddressNotFoundException;
import br.com.ssdev.autoshop.models.Address;
import br.com.ssdev.autoshop.repositories.AddressRepository;
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
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressRequestDTO addressRequestDTO;
    private UUID addressId;

    @BeforeEach
    void setUp() {
        addressId = UUID.randomUUID();
        address = new Address(
                addressId,
                "Main Street",
                "Metropolis",
                "SP",
                "Downtown",
                "12345-678",
                "100",
                "Apt 1"
        );
        addressRequestDTO = new AddressRequestDTO(
                addressId,
                "12345-678",
                "Main Street",
                "Downtown",
                "Metropolis",
                "SP",
                "100",
                "Apt 1"
        );
    }

    @Test
    @DisplayName("Should create address successfully")
    void create_Success() {
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDTO response = addressService.create(addressRequestDTO);

        assertNotNull(response);
        assertEquals("Main Street", response.street());
        assertEquals("Metropolis", response.city());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    @DisplayName("Should return paginated addresses")
    void getAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Address> addressPage = new PageImpl<>(List.of(address));
        when(addressRepository.findAll(pageable)).thenReturn(addressPage);

        Page<AddressResponseDTO> result = addressService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(addressRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return address when ID exists")
    void getById_Success() {
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        AddressResponseDTO response = addressService.getById(addressId);

        assertNotNull(response);
        assertEquals(addressId, response.id());
        verify(addressRepository, times(1)).findById(addressId);
    }

    @Test
    @DisplayName("Should throw AddressNotFoundException when address ID does not exist")
    void getById_NotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(addressRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.getById(invalidId));
        verify(addressRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Should update address successfully")
    void update_Success() {
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDTO response = addressService.update(addressId, addressRequestDTO);

        assertNotNull(response);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    @DisplayName("Should delete address successfully when ID exists")
    void deleteById_Success() {
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        doNothing().when(addressRepository).delete(address);

        assertDoesNotThrow(() -> addressService.deleteById(addressId));
        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    @DisplayName("Should throw AddressNotFoundException when deleting non-existent address")
    void deleteById_NotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(addressRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.deleteById(invalidId));
        verify(addressRepository, never()).delete(any());
    }
}
