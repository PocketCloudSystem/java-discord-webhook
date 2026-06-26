package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ActionRowChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.ContainerChildComponent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public final class ActionRowComponent extends MessageComponent implements ContainerChildComponent {

    private final List<ActionRowChildComponent> components = new ArrayList<>();

    public ActionRowComponent addComponent(ActionRowChildComponent component) {
        components.add(component);
        return this;
    }

    public ActionRowComponent addComponents(ActionRowChildComponent... components) {
        for (ActionRowChildComponent c : components) addComponent(c);
        return this;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.ACTION_ROW;
    }

    @Override
    public Map<String, Object> getComponentData() {
        return Map.of("components", components.stream()
                .map(c -> ((MessageComponent) c).write())
                .toList());
    }

    public static ActionRowComponent create() {
        return new ActionRowComponent();
    }

    public static ActionRowComponent with(ActionRowChildComponent... components) {
        return create().addComponents(components);
    }
}
