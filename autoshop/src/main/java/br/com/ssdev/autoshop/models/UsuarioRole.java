package br.com.ssdev.autoshop.models;

public enum UsuarioRole {
    ADMIN("Admin"),
    USER("Usuario"),
    CONSULTOR("Consultor"),
    TECNICO("Técnico");

    private String role;

  UsuarioRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
