package de.r3pt1s.discord.webhook.message.component.misc;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;

import java.util.LinkedHashMap;
import java.util.Map;

public record MediaGalleryItem(UnfurledMediaItem mediaItem, String description, Boolean spoiler) implements Writeable {

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("media", mediaItem.write());
        map.put("description", description);
        map.put("spoiler", spoiler);
        return WebhookHelper.removeNullFields(map);
    }

    public static MediaGalleryItem create(UnfurledMediaItem mediaItem, String description, Boolean spoiler) {
        return new MediaGalleryItem(mediaItem, description, spoiler);
    }
}
