package de.r3pt1s.discord.webhook.message.component.impl;

import de.r3pt1s.discord.webhook.emoji.PartialEmoji;
import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ActionRowChildComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ButtonStyle;
import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.message.component.misc.SectionAccessoryComponent;
import de.r3pt1s.discord.webhook.util.WebhookHelper;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class ButtonComponent extends MessageComponent implements ActionRowChildComponent, SectionAccessoryComponent {

    private final ButtonStyle style;
    private final Map<String, Object> buttonData;

    private ButtonComponent(String customId, ButtonStyle style, Map<String, Object> buttonData) {
        super();
        this.style = style;
        this.buttonData = buttonData;
        if (customId != null) appendData(Map.of("custom_id", customId));
        appendData(Map.of("style", style.getValue()));
    }

    @Override
    public ComponentType getType() {
        return ComponentType.BUTTON;
    }

    @Override
    public Map<String, Object> getComponentData() {
        return buttonData;
    }

    public static ButtonComponent primary(String customId, String label, PartialEmoji emoji, Boolean disabled) {
        return new ButtonComponent(customId, ButtonStyle.PRIMARY, buildData(null, label, emoji, disabled));
    }

    public static ButtonComponent secondary(String customId, String label, PartialEmoji emoji, Boolean disabled) {
        return new ButtonComponent(customId, ButtonStyle.SECONDARY, buildData(null, label, emoji, disabled));
    }

    public static ButtonComponent success(String customId, String label, PartialEmoji emoji, Boolean disabled) {
        return new ButtonComponent(customId, ButtonStyle.SUCCESS, buildData(null, label, emoji, disabled));
    }

    public static ButtonComponent danger(String customId, String label, PartialEmoji emoji, Boolean disabled) {
        return new ButtonComponent(customId, ButtonStyle.DANGER, buildData(null, label, emoji, disabled));
    }

    public static ButtonComponent link(String url, String label, PartialEmoji emoji, Boolean disabled) {
        return new ButtonComponent(null, ButtonStyle.LINK, buildData(url, label, emoji, disabled));
    }

    public static ButtonComponent premium(String skuId, String label, PartialEmoji emoji, Boolean disabled) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("sku_id", skuId);
        data.put("label", label);
        data.put("emoji", emoji != null ? emoji.write() : null);
        data.put("disabled", disabled);
        return new ButtonComponent(null, ButtonStyle.PREMIUM, WebhookHelper.removeNullFields(data));
    }

    private static Map<String, Object> buildData(String url, String label, PartialEmoji emoji, Boolean disabled) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (url != null) data.put("url", url);
        data.put("label", label);
        data.put("emoji", emoji != null ? emoji.write() : null);
        data.put("disabled", disabled);
        return WebhookHelper.removeNullFields(data);
    }
}
