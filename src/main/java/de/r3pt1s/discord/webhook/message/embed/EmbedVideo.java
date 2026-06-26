package de.r3pt1s.discord.webhook.message.embed;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class EmbedVideo implements Writeable {

    private final String url;
    private final String proxyUrl;
    private final Integer height;
    private final Integer width;

    private EmbedVideo(String url, String proxyUrl, Integer height, Integer width) {
        this.url = url;
        this.proxyUrl = proxyUrl;
        this.height = height;
        this.width = width;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("url", url);
        map.put("proxy_url", proxyUrl);
        map.put("height", height);
        map.put("width", width);
        return WebhookHelper.removeNullFields(map);
    }

    public static EmbedVideo create(String url, String proxyUrl, Integer height, Integer width) {
        return new EmbedVideo(url, proxyUrl, height, width);
    }
}
