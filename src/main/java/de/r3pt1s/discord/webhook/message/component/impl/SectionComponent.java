package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.ContainerChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.SectionAccessoryComponent;
import de.r3pt1s.discord.webhook.message.component.misc.SectionChildComponent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public final class SectionComponent extends MessageComponent implements ContainerChildComponent {

    public static final int MIN_COMPONENTS = 1;
    public static final int MAX_COMPONENTS = 3;

    private final List<SectionChildComponent> components = new ArrayList<>();
    private SectionAccessoryComponent accessory;

    public SectionComponent addComponent(SectionChildComponent component) {
        components.add(component);
        return this;
    }

    public SectionComponent setAccessory(SectionAccessoryComponent accessory) {
        this.accessory = accessory;
        return this;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.SECTION;
    }

    @Override
    public Map<String, Object> getComponentData() {
        if (components.size() < MIN_COMPONENTS || components.size() > MAX_COMPONENTS)
            throw new IllegalStateException("components cannot be less than " + MIN_COMPONENTS + " or greater than " + MAX_COMPONENTS);
        return Map.of(
                "components", components.stream().map(c -> ((MessageComponent) c).write()).toList(),
                "accessory", accessory.write()
        );
    }

    public static SectionComponent create() {
        return new SectionComponent();
    }
}
