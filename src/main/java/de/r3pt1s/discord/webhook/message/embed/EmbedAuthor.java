package de.r3pt1s.discord.webhook.message.embed;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class EmbedAuthor implements Writeable {

    private final String name;
    private final String url;
    private final String iconUrl;
    private final String proxyIconUrl;

    private EmbedAuthor(String name, String url, String iconUrl, String proxyIconUrl) {
        this.name = name;
        this.url = url;
        this.iconUrl = iconUrl;
        this.proxyIconUrl = proxyIconUrl;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("url", url);
        map.put("icon_url", iconUrl);
        map.put("proxy_icon_url", proxyIconUrl);
        return WebhookHelper.removeNullFields(map);
    }

    public static EmbedAuthor create(String name, String url, String iconUrl, String proxyIconUrl) {
        return new EmbedAuthor(name, url, iconUrl, proxyIconUrl);
    }
}
