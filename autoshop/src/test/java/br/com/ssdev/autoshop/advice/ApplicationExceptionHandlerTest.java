package br.com.ssdev.autoshop.advice;

import br.com.ssdev.autoshop.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationExceptionHandlerTest {

    private ApplicationExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ApplicationExceptionHandler();
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException and return field error map")
    void handleInvalidArgument_ReturnsMap() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("userRequestDTO", "email", "The email format is invalid");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        Map<String, String> result = handler.handleInvalidArgument(ex);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The email format is invalid", result.get("email"));
    }

    @Test
    @DisplayName("Should handle NotFoundException and return 404 status map")
    void handleNotFoundException_ReturnsMap() {
        UserNotFoundException ex = new UserNotFoundException("User not found");

        Map<String, Object> result = handler.handleNotFoundException(ex);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.get("status"));
        assertEquals("Not Found", result.get("error"));
        assertEquals("User not found", result.get("message"));
    }

    @Test
    @DisplayName("Should handle BadCredentialsException and return 401 status map")
    void handleBadCredentials_ReturnsMap() {
        BadCredentialsException ex = new BadCredentialsException("Bad credentials");

        Map<String, Object> result = handler.handleBadCredentials(ex);

        assertNotNull(result);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.get("status"));
        assertEquals("Unauthorized", result.get("error"));
        assertEquals("Invalid email or password", result.get("message"));
    }

    @Test
    @DisplayName("Should handle generic Exception and return 500 status map")
    void handleGenericException_ReturnsMap() {
        Exception ex = new RuntimeException("Unexpected error");

        Map<String, Object> result = handler.handleGenericException(ex);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.get("status"));
        assertEquals("Internal Server Error", result.get("error"));
        assertEquals("Unexpected error", result.get("message"));
    }
}
