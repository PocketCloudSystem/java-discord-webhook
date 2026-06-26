package de.r3pt1s.discord.webhook.message.embed;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class EmbedField implements Writeable {

    private final String name;
    private final String value;
    private final Boolean inline;

    private EmbedField(String name, String value, Boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("value", value);
        map.put("inline", inline);
        return WebhookHelper.removeNullFields(map);
    }

    public static EmbedField create(String name, String value, Boolean inline) {
        return new EmbedField(name, value, inline);
    }
}
