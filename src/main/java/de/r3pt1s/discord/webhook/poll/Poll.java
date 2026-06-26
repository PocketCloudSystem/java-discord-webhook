package de.r3pt1s.discord.webhook.poll;

import de.r3pt1s.discord.webhook.emoji.PartialEmoji;
import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class Poll implements Writeable {

    /** Maximum expiry duration in seconds (32 days) */
    public static final long MAX_EXPIRY_TIMESTAMP = 60L * 60 * 24 * 32;
    public static final long DEFAULT_EXPIRY_TIMESTAMP = 60L * 60 * 24;

    private int pollAnswerCounter = 1;
    private final List<PollAnswer> answers = new ArrayList<>();

    private final String question;
    private final String expiry;
    private final boolean allowMultiSelect;
    private final PollLayoutType layoutType;

    private Poll(String question, String expiry, boolean allowMultiSelect, PollLayoutType layoutType) {
        this.question = question;
        this.expiry = expiry;
        this.allowMultiSelect = allowMultiSelect;
        this.layoutType = layoutType;
    }

    public Poll addAnswer(String answer, PartialEmoji emoji) {
        answers.add(new PollAnswer(pollAnswerCounter++, answer, emoji));
        return this;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("question", Map.of("text", question));
        map.put("answers", answers.stream().map(PollAnswer::write).toList());
        map.put("expiry", expiry);
        map.put("allow_multiselect", allowMultiSelect);
        map.put("layout_type", (layoutType != null ? layoutType : PollLayoutType.DEFAULT).getValue());
        return WebhookHelper.removeNullFields(map);
    }

    public static Poll create(String question, Long timestamp, boolean allowMultiSelect, PollLayoutType layoutType) {
        long now = Instant.now().getEpochSecond();
        if (timestamp == null) timestamp = now + DEFAULT_EXPIRY_TIMESTAMP;
        if ((timestamp - now) > MAX_EXPIRY_TIMESTAMP) timestamp = now + MAX_EXPIRY_TIMESTAMP;
        String expiry = DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(timestamp).atOffset(ZoneOffset.UTC));
        return new Poll(question, expiry, allowMultiSelect, layoutType != null ? layoutType : PollLayoutType.DEFAULT);
    }
}
