package com.lucutovidiu.models;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum UserRole {
    ADMIN("ADMIN"), USER("USER"), BUSINESS_ADMIN("BUSINESS_ADMIN"), PRIVATE("PRIVATE");
    private String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static List<String> getUserRolesAsList() {
        return Arrays.asList(ADMIN.getLabel(), USER.getLabel(), BUSINESS_ADMIN.getLabel(), PRIVATE.getLabel());
    }
}
