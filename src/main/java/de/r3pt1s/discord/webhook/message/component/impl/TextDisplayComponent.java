package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.ContainerChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.SectionChildComponent;
import lombok.Getter;

import java.util.Map;

@Getter
public final class TextDisplayComponent extends MessageComponent implements SectionChildComponent, ContainerChildComponent {

    private final String content;

    private TextDisplayComponent(String content) {
        super();
        this.content = content;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.TEXT_DISPLAY;
    }

    @Override
    public Map<String, Object> getComponentData() {
        return Map.of("content", content);
    }

    public static TextDisplayComponent create(String content) {
        return new TextDisplayComponent(content);
    }
}
