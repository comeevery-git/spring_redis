package com.example.concu.infrastructure.campaign.enums;

public enum CampaignStatus {
    ACTIVE("A"),
    DELETED("D"),
    READY("R"),
    SUCCESS("S"),
    ENDED("E");

    private final String value;

    CampaignStatus(String value) {
        this.value = value;
    }
}
