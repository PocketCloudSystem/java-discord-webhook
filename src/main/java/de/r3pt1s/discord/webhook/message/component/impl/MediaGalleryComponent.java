package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.ContainerChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.MediaGalleryItem;
import de.r3pt1s.discord.webhook.message.component.misc.UnfurledMediaItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public final class MediaGalleryComponent extends MessageComponent implements ContainerChildComponent {

    public static final int MAX_DESCRIPTION_LENGTH = 1024;
    public static final int MIN_ITEMS = 1;
    public static final int MAX_ITEMS = 10;

    private final List<MediaGalleryItem> items = new ArrayList<>();

    public MediaGalleryComponent addItem(Object urlOrMediaItem, String description, Boolean spoiler) {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH)
            throw new IllegalArgumentException("Your description is too big");
        UnfurledMediaItem mediaItem = urlOrMediaItem instanceof String s
                ? UnfurledMediaItem.create(s)
                : (UnfurledMediaItem) urlOrMediaItem;
        items.add(new MediaGalleryItem(mediaItem, description, spoiler));
        return this;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.MEDIA_GALLERY;
    }

    @Override
    public Map<String, Object> getComponentData() {
        int count = items.size();
        if (count < MIN_ITEMS || count > MAX_ITEMS)
            throw new IllegalStateException("items cannot be less than " + MIN_ITEMS + " or greater than " + MAX_ITEMS);
        return Map.of("items", items.stream().map(MediaGalleryItem::write).toList());
    }

    public static MediaGalleryComponent create() {
        return new MediaGalleryComponent();
    }
}
