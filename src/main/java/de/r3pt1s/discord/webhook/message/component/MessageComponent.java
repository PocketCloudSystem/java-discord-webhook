package de.r3pt1s.discord.webhook.message.component;

import de.r3pt1s.discord.webhook.message.component.misc.ComponentType;
import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public abstract class MessageComponent implements Writeable {

    private final Map<String, Object> data = new LinkedHashMap<>();

    protected MessageComponent() {
        data.put("type", getType().getValue());
    }

    protected MessageComponent appendData(Map<String, Object> extra) {
        data.putAll(extra);
        return this;
    }

    public abstract ComponentType getType();

    public abstract Map<String, Object> getComponentData();

    @Override
    public Map<String, Object> write() {
        appendData(getComponentData());
        return WebhookHelper.removeNullFields(data);
    }
}
