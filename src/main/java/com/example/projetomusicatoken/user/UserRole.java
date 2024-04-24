package com.example.projetomusicatoken.user;

public enum UserRole {
    USER("USER");
    private String role;
    UserRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }

}
