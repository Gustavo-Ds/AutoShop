package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.UserRequestDTO;
import br.com.ssdev.autoshop.dto.UserResponseDTO;
import br.com.ssdev.autoshop.exceptions.UserNotFoundException;
import br.com.ssdev.autoshop.models.User;
import br.com.ssdev.autoshop.models.UserRole;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User(
                userId,
                "John Doe",
                "john.doe@example.com",
                "secret123",
                UserRole.USER
        );
        userRequestDTO = new UserRequestDTO(
                userId,
                "John Doe",
                "john.doe@example.com",
                "secret123",
                "USER"
        );
    }

    @Test
    @DisplayName("Should create user successfully")
    void create_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.create(userRequestDTO);

        assertNotNull(response);
        assertEquals("John Doe", response.name());
        assertEquals("john.doe@example.com", response.email());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should return paginated users")
    void getAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserResponseDTO> result = userService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return user when ID exists")
    void getById_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.getById(userId);

        assertNotNull(response);
        assertEquals(userId, response.id());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user ID does not exist")
    void getById_NotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(invalidId));
        verify(userRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Should update user successfully")
    void update_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.update(userId, userRequestDTO);

        assertNotNull(response);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user successfully when ID exists")
    void deleteById_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        assertDoesNotThrow(() -> userService.deleteById(userId));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when deleting non-existent user")
    void deleteById_NotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteById(invalidId));
        verify(userRepository, never()).delete(any());
    }
}
