package com.farma.estoque.model;

public enum UserRole {
    ADMIN("ADMIN"),
    OPERADOR("OPERADOR");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}