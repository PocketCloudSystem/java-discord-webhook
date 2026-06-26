package de.r3pt1s.discord.webhook.message;

import de.r3pt1s.discord.webhook.Webhook;
import de.r3pt1s.discord.webhook.WebhookResponse;
import de.r3pt1s.discord.webhook.message.attachment.Attachment;
import de.r3pt1s.discord.webhook.message.component.MessageComponent;
import de.r3pt1s.discord.webhook.message.component.misc.ActionRowChildComponent;
import de.r3pt1s.discord.webhook.message.embed.Embed;
import de.r3pt1s.discord.webhook.message.mention.AllowedMention;
import de.r3pt1s.discord.webhook.poll.Poll;
import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Getter
public final class Message implements Writeable {

    public static final int MAX_CONTENT_CHARACTERS = 2000;
    public static final int MAX_EMBEDS = 10;

    private int internalFileCounter = 0;

    private String content = "";
    private String username = null;
    private String avatarUrl = null;
    private boolean textToSpeech = false;

    private final List<Embed> embeds = new ArrayList<>();
    private AllowedMention allowedMention = null;
    private final List<MessageComponent> components = new ArrayList<>();

    // fileId -> [path, mimeType, fileName]
    private final Map<Integer, String[]> files = new LinkedHashMap<>();
    private final Map<Integer, Attachment> attachments = new LinkedHashMap<>();

    private int flags = 0;
    private String threadName = null;
    private final List<String> threadAppliedTags = new ArrayList<>();
    private Poll poll = null;

    private final boolean wait;
    private final String threadId;
    private final boolean withComponents;
    private final Webhook webhook;

    /**
     * @param wait           Waits for server confirmation before responding (defaults to false)
     * @param threadId       Send to a specific thread within the webhook's channel
     * @param withComponents Whether to respect the components field of the request
     * @param webhook        The parent webhook (may be null when constructing standalone)
     */
    public Message(boolean wait, String threadId, boolean withComponents, Webhook webhook) {
        this.wait = wait;
        this.threadId = threadId;
        this.withComponents = withComponents;
        this.webhook = webhook;
    }

    public Message(boolean wait, String threadId, boolean withComponents) {
        this(wait, threadId, withComponents, null);
    }

    public Message() {
        this(false, null, false, null);
    }

    /**
     * Sends the message via the webhook this message was created from.
     */
    public CompletableFuture<WebhookResponse> send() {
        if (webhook == null) throw new IllegalStateException("Please create a message via Webhook.createMessage()");
        return webhook.send(this);
    }

    /**
     * Sends the message via a different webhook.
     */
    public CompletableFuture<WebhookResponse> sendWithDiffWebhook(Webhook other) {
        return other.send(this);
    }

    /**
     * Allows modifying the message inline without breaking the builder chain.
     */
    public Message tap(Consumer<Message> tapFn) {
        tapFn.accept(this);
        return this;
    }

    /**
     * Sets the message content (capped at {@value MAX_CONTENT_CHARACTERS} characters).
     */
    public Message setContent(String text) {
        if (text.length() > MAX_CONTENT_CHARACTERS) text = text.substring(0, MAX_CONTENT_CHARACTERS);
        this.content = text;
        return this;
    }

    public Message setUsername(String username) {
        this.username = username;
        return this;
    }

    public Message setAvatarUrl(String avatarUrl) {
        WebhookHelper.validateUrl(avatarUrl, "AvatarUrl");
        this.avatarUrl = avatarUrl;
        return this;
    }

    public Message setTextToSpeech(boolean textToSpeech) {
        this.textToSpeech = textToSpeech;
        return this;
    }

    /**
     * @see Embed#create()
     */
    public Message addEmbed(Embed embed) {
        if (embeds.size() == MAX_EMBEDS)
            throw new IllegalStateException("Failed to add embed, max amount of embeds (" + MAX_EMBEDS + ") reached");
        embeds.add(embed);
        return this;
    }

    public Message addEmbedIf(BooleanSupplier condition, Embed embed) {
        if (condition.getAsBoolean()) addEmbed(embed);
        return this;
    }

    public Message addEmbeds(Embed... embeds) {
        for (Embed e : embeds) addEmbed(e);
        return this;
    }

    public Message addEmbedsIf(BooleanSupplier condition, Embed... embeds) {
        if (condition.getAsBoolean()) addEmbeds(embeds);
        return this;
    }

    /**
     * @see AllowedMention#create()
     */
    public Message setAllowedMention(AllowedMention allowedMention) {
        this.allowedMention = allowedMention;
        return this;
    }

    public Message addComponent(MessageComponent component) {
        if (component instanceof ActionRowChildComponent)
            throw new IllegalArgumentException(component.getType().name() + " components need to be wrapped around an ActionRow");
        components.add(component);
        return this;
    }

    /**
     * Attaches a file to the message.
     *
     * @param filePath       Absolute or relative path to the file
     * @param mimeType       Optional MIME type (probed automatically when null)
     * @param postedFileName Optional file name shown in Discord (defaults to the actual file name)
     */
    public Message addFile(String filePath, String mimeType, String postedFileName) throws Exception {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) throw new IllegalArgumentException("File " + filePath + " does not exist");

        if (mimeType == null) mimeType = Files.probeContentType(path);
        if (mimeType == null) mimeType = "application/octet-stream";

        if (postedFileName == null) postedFileName = path.getFileName().toString();

        int attachmentId = internalFileCounter++;
        files.put(attachmentId, new String[]{filePath, mimeType, postedFileName});
        attachments.put(attachmentId, new Attachment(attachmentId, postedFileName));
        return this;
    }

    public Message addFile(String filePath) throws Exception {
        return addFile(filePath, null, null);
    }

    public Message addFlag(MessageFlag flag) {
        this.flags |= flag.getValue();
        return this;
    }

    public Message removeFlags(MessageFlag... flags) {
        for (MessageFlag f : flags) this.flags &= ~f.getValue();
        return this;
    }

    /**
     * If set, a new thread with the given name will be created.
     */
    public Message setThreadName(String threadName) {
        this.threadName = threadName;
        return this;
    }

    /**
     * Sets the tag IDs that will be applied to the created thread.
     */
    public Message setThreadAppliedTags(List<String> threadAppliedTagIds) {
        threadAppliedTags.clear();
        threadAppliedTags.addAll(threadAppliedTagIds);
        return this;
    }

    /**
     * @see Poll#create(String, Long, boolean, de.r3pt1s.discord.webhook.poll.PollLayoutType)
     */
    public Message setPoll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public boolean isFlagSet(MessageFlag flag) {
        return (flags & flag.getValue()) != 0;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("content", content);
        data.put("tts", textToSpeech);
        data.put("embeds", embeds.stream().map(Embed::write).toList());

        if (username != null) data.put("username", username);
        if (avatarUrl != null) data.put("avatar_url", avatarUrl);
        if (allowedMention != null) data.put("allowed_mentions", allowedMention.write());
        if (!components.isEmpty()) data.put("components", components.stream().map(MessageComponent::write).toList());
        if (flags != 0) data.put("flags", flags);
        if (threadName != null) data.put("thread_name", threadName);
        if (!threadAppliedTags.isEmpty()) data.put("applied_tags", threadAppliedTags);
        if (poll != null) data.put("poll", poll.write());

        if (!files.isEmpty()) {
            data.put("attachments", attachments.values().stream().map(Attachment::write).toList());
            data.put("files", files);
        }

        return data;
    }
}
