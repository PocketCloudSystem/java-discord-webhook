package de.r3pt1s.discord.webhook.poll;

import de.r3pt1s.discord.webhook.emoji.PartialEmoji;
import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;

import java.util.LinkedHashMap;
import java.util.Map;

public record PollAnswer(int answerId, String answer, PartialEmoji emoji) implements Writeable {

    @Override
    public Map<String, Object> write() {
        Map<String, Object> pollMedia = new LinkedHashMap<>();
        pollMedia.put("text", answer);
        pollMedia.put("emoji", emoji != null ? emoji.write() : null);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("answer_id", answerId);
        map.put("poll_media", WebhookHelper.removeNullFields(pollMedia));
        return WebhookHelper.removeNullFields(map);
    }
}
