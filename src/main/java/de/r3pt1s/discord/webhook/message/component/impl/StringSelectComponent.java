package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.emoji.PartialEmoji;
import de.r3pt1s.discord.webhook.message.component.CustomComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ActionRowChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentConstants;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.SelectOption;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class StringSelectComponent extends CustomComponent implements ActionRowChildComponent {

    public static final int MAX_OPTIONS = 25;

    private final List<SelectOption> options = new ArrayList<>();
    private final String placeholder;
    private final Integer minValues;
    private final Integer maxValues;
    private final Boolean required;
    private final Boolean disabled;

    private StringSelectComponent(String customId, String placeholder, Integer minValues, Integer maxValues, Boolean required, Boolean disabled) {
        super(customId);
        this.placeholder = placeholder;
        this.minValues = minValues;
        this.maxValues = maxValues;
        this.required = required;
        this.disabled = disabled;

        if (placeholder != null && placeholder.length() > ComponentConstants.MAX_PLACEHOLDER_LENGTH)
            throw new IllegalArgumentException("placeholder length is too large, max is " + ComponentConstants.MAX_PLACEHOLDER_LENGTH);
        if (minValues != null && (minValues < ComponentConstants.MIN_MIN_VALUES || minValues > ComponentConstants.MAX_MIN_VALUES))
            throw new IllegalArgumentException("minValues out of range");
        if (maxValues != null && (maxValues < ComponentConstants.MIN_MAX_VALUES || maxValues > ComponentConstants.MAX_MAX_VALUES))
            throw new IllegalArgumentException("maxValues out of range");
    }

    public StringSelectComponent addOption(String label, String value, String description, PartialEmoji emoji, Boolean isDefault) {
        if (options.size() == MAX_OPTIONS) throw new IllegalStateException("Max options (" + MAX_OPTIONS + ") reached");
        options.add(SelectOption.create(label, value, description, emoji, isDefault));
        return this;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.STRING_SELECT;
    }

    @Override
    public Map<String, Object> getComponentData() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("placeholder", placeholder);
        map.put("options", options.stream().map(SelectOption::write).toList());
        map.put("min_values", minValues);
        map.put("max_values", maxValues);
        map.put("required", required);
        map.put("disabled", disabled);
        return map;
    }

    public static StringSelectComponent create(String customId, String placeholder, Integer minValues, Integer maxValues, Boolean required, Boolean disabled) {
        return new StringSelectComponent(customId, placeholder, minValues, maxValues, required, disabled);
    }
}
