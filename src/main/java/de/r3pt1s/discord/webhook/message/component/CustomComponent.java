package de.r3pt1s.discord.webhook.message.component;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class CustomComponent extends MessageComponent {

    public static final int MIN_CUSTOM_ID_LENGTH = 1;
    public static final int MAX_CUSTOM_ID_LENGTH = 100;

    private final String customId;

    protected CustomComponent(String customId) {
        super();
        if (customId == null || customId.isEmpty())
            throw new IllegalArgumentException("customId cannot be empty");
        if (customId.length() < MIN_CUSTOM_ID_LENGTH || customId.length() > MAX_CUSTOM_ID_LENGTH)
            throw new IllegalArgumentException("customId cannot be less than " + MIN_CUSTOM_ID_LENGTH + " or greater than " + MAX_CUSTOM_ID_LENGTH);
        this.customId = customId;
        appendData(Map.of("custom_id", customId));
    }
}
