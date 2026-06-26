package de.r3pt1s.discord.webhook.message;

import lombok.Getter;

@Getter
public enum MessageFlag {

    SUPPRESS_EMBEDS(1 << 2),
    SUPPRESS_NOTIFICATIONS(1 << 12),
    IS_COMPONENTS_V2(1 << 15);

    private final int value;

    MessageFlag(int value) {
        this.value = value;
    }
}
