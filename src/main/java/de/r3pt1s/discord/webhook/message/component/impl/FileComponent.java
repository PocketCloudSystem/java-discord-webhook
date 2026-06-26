package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.ContainerChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.UnfurledMediaItem;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class FileComponent extends MessageComponent implements ContainerChildComponent {

    private final UnfurledMediaItem mediaItem;
    private final Boolean spoiler;

    private FileComponent(UnfurledMediaItem mediaItem, Boolean spoiler) {
        super();
        this.mediaItem = mediaItem;
        this.spoiler = spoiler;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.FILE;
    }

    @Override
    public Map<String, Object> getComponentData() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("file", mediaItem.write());
        map.put("spoiler", spoiler);
        return map;
    }

    public static FileComponent create(Object attachmentFileNameOrMediaItem, Boolean spoiler) {
        UnfurledMediaItem mediaItem;
        if (attachmentFileNameOrMediaItem instanceof String s) {
            String url = s.startsWith("attachment://") ? s : "attachment://" + s;
            mediaItem = UnfurledMediaItem.create(url);
        } else {
            mediaItem = (UnfurledMediaItem) attachmentFileNameOrMediaItem;
        }
        return new FileComponent(mediaItem, spoiler);
    }
}
