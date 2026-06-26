package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.SectionAccessoryComponent;
import de.r3pt1s.discord.webhook.message.component.misc.UnfurledMediaItem;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class ThumbnailComponent extends MessageComponent implements SectionAccessoryComponent {

    private final UnfurledMediaItem mediaItem;
    private final String description;
    private final Boolean spoiler;

    private ThumbnailComponent(UnfurledMediaItem mediaItem, String description, Boolean spoiler) {
        super();
        this.mediaItem = mediaItem;
        this.description = description;
        this.spoiler = spoiler;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.THUMBNAIL;
    }

    @Override
    public Map<String, Object> getComponentData() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("media", mediaItem.write());
        map.put("description", description);
        map.put("spoiler", spoiler);
        return map;
    }

    public static ThumbnailComponent create(Object urlOrMediaItem, String description, Boolean spoiler) {
        UnfurledMediaItem mediaItem = urlOrMediaItem instanceof String s ? UnfurledMediaItem.create(s) : (UnfurledMediaItem) urlOrMediaItem;
        return new ThumbnailComponent(mediaItem, description, spoiler);
    }
}
