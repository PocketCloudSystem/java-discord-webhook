package de.r3pt1s.discord.webhook.message.attachment;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Attachment implements Writeable {

    private final int id;
    private final String fileName;

    public Attachment(int id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("filename", fileName);
        return WebhookHelper.removeNullFields(map);
    }
}
