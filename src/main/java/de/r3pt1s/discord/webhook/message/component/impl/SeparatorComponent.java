package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.ContainerChildComponent;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class SeparatorComponent extends MessageComponent implements ContainerChildComponent {

    private final Boolean divider;
    private final Integer spacing;

    public SeparatorComponent(Boolean divider, Integer spacing) {
        super();
        this.divider = divider;
        this.spacing = spacing;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.SEPARATOR;
    }

    @Override
    public Map<String, Object> getComponentData() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("divider", divider);
        map.put("spacing", spacing);
        return map;
    }

    public static SeparatorComponent create(Boolean divider, Integer spacing) {
        return new SeparatorComponent(divider, spacing);
    }
}
