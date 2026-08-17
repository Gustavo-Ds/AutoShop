package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.CustomerRequestDTO;
import br.com.ssdev.autoshop.dto.CustomerResponseDTO;
import br.com.ssdev.autoshop.exceptions.AddressNotFoundException;
import br.com.ssdev.autoshop.exceptions.CustomerNotFoundException;
import br.com.ssdev.autoshop.exceptions.UserNotFoundException;
import br.com.ssdev.autoshop.models.Address;
import br.com.ssdev.autoshop.models.Customer;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.repositories.AddressRepository;
import br.com.ssdev.autoshop.repositories.CustomerRepository;
import br.com.ssdev.autoshop.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    private static final String MESSAGE = "Customer not found";

    public CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO) {
        User user = userRepository.findById(customerRequestDTO.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Address address = addressRepository.findById(customerRequestDTO.addressId())
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequestDTO, customer);
        customer.setUser(user);
        customer.setAddress(address);

        customerRepository.save(customer);
        return new CustomerResponseDTO(customer);
    }

    public Page<CustomerResponseDTO> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(CustomerResponseDTO::new);
    }

    public CustomerResponseDTO getById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(MESSAGE));
        return new CustomerResponseDTO(customer);
    }

    public CustomerResponseDTO update(UUID id, CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(MESSAGE));

        User user = userRepository.findById(customerRequestDTO.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Address address = addressRepository.findById(customerRequestDTO.addressId())
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));

        customer.setUser(user);
        customer.setAddress(address);
        customer.setPhone(customerRequestDTO.phone());

        customerRepository.save(customer);
        return new CustomerResponseDTO(customer);
    }

    public void deleteById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(MESSAGE));
        customerRepository.delete(customer);
    }
}
