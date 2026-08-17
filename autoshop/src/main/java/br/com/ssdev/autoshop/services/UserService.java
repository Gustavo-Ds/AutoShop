package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.UserRequestDTO;
import br.com.ssdev.autoshop.dto.UserResponseDTO;
import br.com.ssdev.autoshop.exceptions.UserNotFoundException;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.models.UserRole;
import br.com.ssdev.autoshop.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final String MESSAGE = "User not found";

    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRequestDTO, user);

        if (userRequestDTO.role() != null && !userRequestDTO.role().isBlank()) {
            try {
                user.setRole(UserRole.valueOf(userRequestDTO.role().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setRole(UserRole.USER);
            }
        } else {
            user.setRole(UserRole.USER);
        }

        userRepository.save(user);
        return new UserResponseDTO(user);
    }

    public Page<UserResponseDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponseDTO::new);
    }

    public UserResponseDTO getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(MESSAGE));
        return new UserResponseDTO(user);
    }

    public UserResponseDTO update(UUID id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(MESSAGE));

        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        if (userRequestDTO.password() != null && !userRequestDTO.password().isBlank()) {
            user.setPassword(userRequestDTO.password());
        }
        if (userRequestDTO.role() != null && !userRequestDTO.role().isBlank()) {
            try {
                user.setRole(UserRole.valueOf(userRequestDTO.role().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // keep existing role
            }
        }

        userRepository.save(user);
        return new UserResponseDTO(user);
    }

    public void deleteById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(MESSAGE));
        userRepository.delete(user);
    }
}
