package com.example.concu.infrastructure.applier.enums;

public enum ApplierUserStatus {
    ACTIVE("A"),
    DELETED("D"),
    CANCEL("C"),
    READY("R");

    private final String value;

    ApplierUserStatus(String value) {
        this.value = value;
    }
}
