package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.ContainerChildComponent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class ContainerComponent extends MessageComponent {

    private final List<ContainerChildComponent> components = new ArrayList<>();
    private final Integer accentColor;
    private final Boolean spoiler;

    private ContainerComponent(Integer accentColor, Boolean spoiler) {
        super();
        this.accentColor = accentColor;
        this.spoiler = spoiler;
    }

    public ContainerComponent addComponent(ContainerChildComponent component) {
        components.add(component);
        return this;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.CONTAINER;
    }

    @Override
    public Map<String, Object> getComponentData() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("components", components.stream().map(c -> ((MessageComponent) c).write()).toList());
        map.put("accent_color", accentColor);
        map.put("spoiler", spoiler);
        return map;
    }

    public static ContainerComponent create(Integer accentColor, Boolean spoiler) {
        return new ContainerComponent(accentColor, spoiler);
    }
}
