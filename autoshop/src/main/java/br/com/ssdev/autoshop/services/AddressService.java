package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.AddressRequestDTO;
import br.com.ssdev.autoshop.dto.AddressResponseDTO;
import br.com.ssdev.autoshop.exceptions.AddressNotFoundException;
import br.com.ssdev.autoshop.models.Address;
import br.com.ssdev.autoshop.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    private static final String MESSAGE = "Address not found";

    public AddressResponseDTO create(AddressRequestDTO addressRequestDTO) {
        Address address = new Address();
        address.setCep(addressRequestDTO.cep());
        address.setStreet(addressRequestDTO.street());
        address.setNeighborhood(addressRequestDTO.neighborhood());
        address.setCity(addressRequestDTO.city());
        address.setState(addressRequestDTO.state());
        address.setNumber(addressRequestDTO.number());
        address.setComplement(addressRequestDTO.complement());

        addressRepository.save(address);
        return new AddressResponseDTO(address);
    }

    public Page<AddressResponseDTO> getAll(Pageable pageable) {
        return addressRepository.findAll(pageable).map(AddressResponseDTO::new);
    }

    public AddressResponseDTO getById(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(MESSAGE));
        return new AddressResponseDTO(address);
    }

    public AddressResponseDTO update(UUID id, AddressRequestDTO addressRequestDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(MESSAGE));

        address.setCep(addressRequestDTO.cep());
        address.setStreet(addressRequestDTO.street());
        address.setNeighborhood(addressRequestDTO.neighborhood());
        address.setCity(addressRequestDTO.city());
        address.setState(addressRequestDTO.state());
        address.setNumber(addressRequestDTO.number());
        address.setComplement(addressRequestDTO.complement());

        addressRepository.save(address);
        return new AddressResponseDTO(address);
    }

    public void deleteById(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(MESSAGE));
        addressRepository.delete(address);
    }
}
