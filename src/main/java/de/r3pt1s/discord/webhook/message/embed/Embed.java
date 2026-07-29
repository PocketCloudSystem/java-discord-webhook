package de.r3pt1s.discord.webhook.message.embed;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

@Getter
public final class Embed implements Writeable {

    public static final int MAX_FIELDS = 25;
    public static final int MAX_TITLE_LENGTH = 256;
    public static final int MAX_DESCRIPTION_LENGTH = 4096;

    private String title;
    private String description;
    private String url;
    private Long timestamp;
    private Integer color;

    private final List<EmbedField> fields = new ArrayList<>();

    private EmbedAuthor author;
    private EmbedFooter footer;
    private EmbedImage image;
    private EmbedImage thumbnail;
    private EmbedVideo video;
    private EmbedProvider provider;

    private Embed() {}

    public Embed setTitle(String title) {
        if (title != null && title.length() > MAX_TITLE_LENGTH)
            throw new IllegalArgumentException("Embed titles are limited to " + MAX_TITLE_LENGTH + " characters");
        this.title = title;
        return this;
    }

    public Embed setDescription(String description) {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH)
            throw new IllegalArgumentException("Embed descriptions are limited to " + MAX_DESCRIPTION_LENGTH + " characters");
        this.description = description;
        return this;
    }

    public Embed setUrl(String url) {
        WebhookHelper.validateUrl(url);
        this.url = url;
        return this;
    }

    public Embed setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Embed setTimestampNow() {
        this.timestamp = Instant.now().getEpochSecond();
        return this;
    }

    public Embed setColor(Color color) {
        return setColorRgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Embed setColorRgb(int red, int green, int blue) {
        return setColorHex(String.format("#%02x%02x%02x", red, green, blue));
    }

    public Embed setColorHex(String hex) {
        this.color = Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
        return this;
    }

    public Embed addField(String name, String value, Boolean inline) {
        if (fields.size() == MAX_FIELDS) throw new IllegalStateException("Max fields (" + MAX_FIELDS + ") reached");
        fields.add(EmbedField.create(name, value, inline));
        return this;
    }

    public Embed addFieldIf(BooleanSupplier condition, String name, String value, Boolean inline) {
        if (condition.getAsBoolean()) addField(name, value, inline);
        return this;
    }

    public Embed addFields(EmbedField... embedFields) {
        for (EmbedField f : embedFields) fields.add(f);
        return this;
    }

    public Embed addFieldsIf(BooleanSupplier condition, EmbedField... embedFields) {
        if (condition.getAsBoolean()) addFields(embedFields);
        return this;
    }

    public Embed setAuthor(String name, String url, String iconUrl, String proxyIconUrl) {
        WebhookHelper.validateUrl(url);
        WebhookHelper.validateUrl(iconUrl, "IconUrl");
        WebhookHelper.validateUrl(proxyIconUrl, "ProxyIconUrl");
        this.author = EmbedAuthor.create(name, url, iconUrl, proxyIconUrl);
        return this;
    }

    public Embed setFooter(String text, String iconUrl, String proxyIconUrl) {
        WebhookHelper.validateUrl(iconUrl, "IconUrl");
        WebhookHelper.validateUrl(proxyIconUrl, "ProxyIconUrl");
        this.footer = EmbedFooter.create(text, iconUrl, proxyIconUrl);
        return this;
    }

    public Embed setImage(String url, String proxyUrl, Integer height, Integer width) {
        WebhookHelper.validateUrl(url);
        WebhookHelper.validateUrl(proxyUrl, "ProxyUrl");
        this.image = EmbedImage.create(url, proxyUrl, height, width);
        return this;
    }

    public Embed setThumbnail(String url, String proxyUrl, Integer height, Integer width) {
        WebhookHelper.validateUrl(url);
        WebhookHelper.validateUrl(proxyUrl, "ProxyUrl");
        this.thumbnail = EmbedImage.create(url, proxyUrl, height, width);
        return this;
    }

    public Embed setVideo(String url, String proxyUrl, Integer height, Integer width) {
        WebhookHelper.validateUrl(url);
        WebhookHelper.validateUrl(proxyUrl, "ProxyUrl");
        this.video = EmbedVideo.create(url, proxyUrl, height, width);
        return this;
    }

    public Embed setProvider(String name, String url) {
        this.provider = EmbedProvider.create(name, url);
        return this;
    }

    public Embed clearFields() {
        fields.clear();
        return this;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("url", url);
        map.put("timestamp", timestamp != null
                ? DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(timestamp).atOffset(ZoneOffset.UTC))
                : null);
        map.put("color", color);
        map.put("fields", fields.stream().map(EmbedField::write).toList());
        map.put("author", author != null ? author.write() : null);
        map.put("footer", footer != null ? footer.write() : null);
        map.put("image", image != null ? image.write() : null);
        map.put("thumbnail", thumbnail != null ? thumbnail.write() : null);
        map.put("video", video != null ? video.write() : null);
        map.put("provider", provider != null ? provider.write() : null);
        return WebhookHelper.removeNullFields(map);
    }

    public static Embed create() {
        return new Embed();
    }
}
