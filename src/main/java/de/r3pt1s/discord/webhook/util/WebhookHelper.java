package de.r3pt1s.discord.webhook.util;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WebhookHelper {

    private WebhookHelper() {}

    public static void validateUrl(String url, String name) {
        if (url != null) {
            try {
                URI uri = URI.create(url);
                String scheme = uri.getScheme();
                if (scheme == null || (!scheme.equals("http") && !scheme.equals("https"))) {
                    throw new IllegalArgumentException((name != null ? name : "Url") + " must be a valid URL");
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException((name != null ? name : "Url") + " must be a valid URL");
            }
        }
    }

    public static void validateUrl(String url) {
        validateUrl(url, null);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> removeNullFields(Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            Object value = entry.getValue();
            if (value == null) continue;

            if (value instanceof Map<?, ?> map) {
                Map<String, Object> cleaned = removeNullFields((Map<String, Object>) map);
                if (!cleaned.isEmpty()) result.put(entry.getKey(), cleaned);
            } else if (value instanceof List<?> list) {
                if (!list.isEmpty()) result.put(entry.getKey(), value);
            } else {
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }
}
