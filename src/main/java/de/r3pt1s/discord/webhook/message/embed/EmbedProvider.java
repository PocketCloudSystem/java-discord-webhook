package de.r3pt1s.discord.webhook.message.embed;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class EmbedProvider implements Writeable {

    private final String name;
    private final String url;

    private EmbedProvider(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("url", url);
        return WebhookHelper.removeNullFields(map);
    }

    public static EmbedProvider create(String name, String url) {
        return new EmbedProvider(name, url);
    }
}
