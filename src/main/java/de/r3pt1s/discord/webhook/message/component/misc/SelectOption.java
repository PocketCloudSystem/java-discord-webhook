package de.r3pt1s.discord.webhook.message.component.misc;

import de.r3pt1s.discord.webhook.emoji.PartialEmoji;
import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class SelectOption implements Writeable {

    private final String label;
    private final String value;
    private final String description;
    private final PartialEmoji emoji;
    private final Boolean isDefault;

    private SelectOption(String label, String value, String description, PartialEmoji emoji, Boolean isDefault) {
        this.label = label;
        this.value = value;
        this.description = description;
        this.emoji = emoji;
        this.isDefault = isDefault;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("label", label);
        map.put("value", value);
        map.put("description", description);
        map.put("emoji", emoji != null ? emoji.write() : null);
        map.put("default", isDefault);
        return WebhookHelper.removeNullFields(map);
    }

    public static SelectOption create(String label, String value, String description, PartialEmoji emoji, Boolean isDefault) {
        return new SelectOption(label, value, description, emoji, isDefault);
    }
}
