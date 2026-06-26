package de.r3pt1s.discord.webhook.message.component.misc;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class UnfurledMediaItem implements Writeable {

    private final String url;

    private UnfurledMediaItem(String url) {
        this.url = url;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("url", url);
        return WebhookHelper.removeNullFields(map);
    }

    public static UnfurledMediaItem create(String url) {
        return new UnfurledMediaItem(url);
    }
}
