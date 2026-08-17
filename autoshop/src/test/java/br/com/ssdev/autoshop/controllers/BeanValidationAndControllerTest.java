package br.com.ssdev.autoshop.controllers;

import br.com.ssdev.autoshop.dto.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeanValidationAndControllerTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should detect invalid email format and blank name in UserRequestDTO")
    void userRequestDTO_ValidationErrors() {
        UserRequestDTO invalidUser = new UserRequestDTO(
                null,
                "", // Blank name
                "invalid-email-format", // Invalid email
                "123", // Password too short (< 6)
                "" // Blank role
        );

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(invalidUser);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The name cannot be empty")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The email format is invalid")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The password must be at least 6 characters long")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The role cannot be empty")));
    }

    @Test
    @DisplayName("Should detect null UUIDs and invalid phone number in CustomerRequestDTO")
    void customerRequestDTO_ValidationErrors() {
        CustomerRequestDTO invalidCustomer = new CustomerRequestDTO(
                null, // Null addressId
                "abc", // Invalid phone format
                null  // Null userId
        );

        Set<ConstraintViolation<CustomerRequestDTO>> violations = validator.validate(invalidCustomer);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The address ID cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The user ID cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The phone number must be valid")));
    }

    @Test
    @DisplayName("Should detect invalid license plate and year < 1900 in VehicleRequestDTO")
    void vehicleRequestDTO_ValidationErrors() {
        VehicleRequestDTO invalidVehicle = new VehicleRequestDTO(
                null,
                null, // Null customerId
                "",   // Blank brand
                "",   // Blank model
                "INVALID_PLATE", // Invalid plate regex
                "",   // Blank chassis
                1850  // Year < 1900
        );

        Set<ConstraintViolation<VehicleRequestDTO>> violations = validator.validate(invalidVehicle);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The customer ID cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The license plate must be valid")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The year must be greater than or equal to 1900")));
    }

    @Test
    @DisplayName("Should detect negative finalValue and missing vehicleId in ServiceOrderRequestDTO")
    void serviceOrderRequestDTO_ValidationErrors() {
        ServiceOrderRequestDTO invalidOrder = new ServiceOrderRequestDTO(
                null,
                null,
                null, // Null vehicleId
                null, // Null mechanicId
                null,
                null,
                "",   // Blank service
                "",   // Blank description
                null,
                new BigDecimal("-50.00") // Negative value
        );

        Set<ConstraintViolation<ServiceOrderRequestDTO>> violations = validator.validate(invalidOrder);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The vehicle ID cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The mechanic ID cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The service title cannot be empty")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The final value must be zero or positive")));
    }

    @Test
    @DisplayName("Should validate valid AddressRequestDTO without any violations")
    void addressRequestDTO_SuccessValidation() {
        AddressRequestDTO validAddress = new AddressRequestDTO(
                UUID.randomUUID(),
                "12345-678",
                "Main Street",
                "Downtown",
                "Metropolis",
                "SP",
                "100",
                "Suite 4"
        );

        Set<ConstraintViolation<AddressRequestDTO>> violations = validator.validate(validAddress);

        assertTrue(violations.isEmpty());
    }
}
