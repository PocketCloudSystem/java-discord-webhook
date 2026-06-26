package de.r3pt1s.discord.webhook.message.component.misc;

public enum ButtonStyle {

    PRIMARY(1),
    SECONDARY(2),
    SUCCESS(3),
    DANGER(4),
    LINK(5),
    PREMIUM(6);

    private final int value;

    ButtonStyle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
