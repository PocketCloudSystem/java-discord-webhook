package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.message.component.CustomComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ActionRowChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.DefaultValueRepresentationType;
import de.r3pt1s.discord.webhook.message.component.misc.SelectDefaultValue;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class ChannelSelectComponent extends CustomComponent implements ActionRowChildComponent {

    private final List<SelectDefaultValue> defaultValues = new ArrayList<>();
    private final String placeholder;
    private final Integer minValues;
    private final Integer maxValues;
    private final Boolean required;
    private final Boolean disabled;

    private ChannelSelectComponent(String customId, String placeholder, Integer minValues, Integer maxValues, Boolean required, Boolean disabled) {
        super(customId);
        this.placeholder = placeholder;
        this.minValues = minValues;
        this.maxValues = maxValues;
        this.required = required;
        this.disabled = disabled;
    }

    public ChannelSelectComponent addDefaultValue(String channelId) {
        defaultValues.add(SelectDefaultValue.create(channelId, DefaultValueRepresentationType.CHANNEL));
        return this;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.CHANNEL_SELECT;
    }

    @Override
    public Map<String, Object> getComponentData() {
        int count = defaultValues.size();
        if (minValues != null && count < minValues)
            throw new IllegalStateException("defaultValues cannot be less than " + minValues);
        if (maxValues != null && count > maxValues)
            throw new IllegalStateException("defaultValues cannot be greater than " + maxValues);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("default_values", defaultValues.stream().map(SelectDefaultValue::write).toList());
        map.put("placeholder", placeholder);
        map.put("min_values", minValues);
        map.put("max_values", maxValues);
        map.put("required", required);
        map.put("disabled", disabled);
        return map;
    }

    public static ChannelSelectComponent create(String customId, String placeholder, Integer minValues, Integer maxValues, Boolean required, Boolean disabled) {
        return new ChannelSelectComponent(customId, placeholder, minValues, maxValues, required, disabled);
    }
}
