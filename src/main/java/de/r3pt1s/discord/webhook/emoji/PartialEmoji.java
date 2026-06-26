package de.r3pt1s.discord.webhook.emoji;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;

import java.util.LinkedHashMap;
import java.util.Map;

public record PartialEmoji(String emojiId, String emojiName) implements Writeable {

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", emojiId);
        map.put("name", emojiName);
        return WebhookHelper.removeNullFields(map);
    }

    public static PartialEmoji create(String emojiId, String emojiName) {
        return new PartialEmoji(emojiId, emojiName);
    }

    public static PartialEmoji fromUnicode(String unicode) {
        return new PartialEmoji(null, unicode);
    }
}
