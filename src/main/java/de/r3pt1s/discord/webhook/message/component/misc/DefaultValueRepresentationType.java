package de.r3pt1s.discord.webhook.message.component.misc;

public enum DefaultValueRepresentationType {

    USER("user"),
    ROLE("role"),
    CHANNEL("channel");

    private final String value;

    DefaultValueRepresentationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
