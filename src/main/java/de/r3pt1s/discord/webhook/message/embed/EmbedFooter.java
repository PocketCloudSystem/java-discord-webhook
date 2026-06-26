package de.r3pt1s.discord.webhook.message.embed;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class EmbedFooter implements Writeable {

    private final String text;
    private final String iconUrl;
    private final String proxyIconUrl;

    private EmbedFooter(String text, String iconUrl, String proxyIconUrl) {
        this.text = text;
        this.iconUrl = iconUrl;
        this.proxyIconUrl = proxyIconUrl;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("text", text);
        map.put("icon_url", iconUrl);
        map.put("proxy_icon_url", proxyIconUrl);
        return WebhookHelper.removeNullFields(map);
    }

    public static EmbedFooter create(String text, String iconUrl, String proxyIconUrl) {
        return new EmbedFooter(text, iconUrl, proxyIconUrl);
    }
}
