package de.r3pt1s.discord.webhook.message.component.misc;

import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class SelectDefaultValue implements Writeable {

    private final String snowflakeId;
    private final DefaultValueRepresentationType representationType;

    private SelectDefaultValue(String snowflakeId, DefaultValueRepresentationType representationType) {
        this.snowflakeId = snowflakeId;
        this.representationType = representationType;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", snowflakeId);
        map.put("type", representationType.getValue());
        return map;
    }

    public static SelectDefaultValue create(String snowflakeId, DefaultValueRepresentationType representationType) {
        return new SelectDefaultValue(snowflakeId, representationType);
    }
}
