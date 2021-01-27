package com.lucutovidiu.models;


public enum UserRole {
    ADMIN("ADMIN"), USER("USER"), BUSINESS_ADMIN("BUSINESS_ADMIN"), PRIVATE("PRIVATE");
    private String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
