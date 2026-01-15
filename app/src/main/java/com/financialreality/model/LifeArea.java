package com.financialreality.model;

public enum LifeArea {
    HEALTH("Health"),
    FINANCE("Finance"),
    CAREER("Career"),
    RELATIONSHIPS("Relationships");

    private final String displayName;

    LifeArea(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
