package br.com.ssdev.autoshop.models;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("Admin"),
    MECHANIC("Mechanic"),
    CONSULTANT("Consultant"),
    USER("User");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
