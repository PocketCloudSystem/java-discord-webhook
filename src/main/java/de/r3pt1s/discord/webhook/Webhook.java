package de.r3pt1s.discord.webhook;

import de.r3pt1s.discord.webhook.message.Message;
import de.r3pt1s.discord.webhook.util.MultipartBodyPublisher;
import lombok.Getter;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@Getter
public final class Webhook {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String defaultUsername = "";
    private String defaultAvatarUrl = "";

    private final String url;

    /**
     * @param url The base Discord webhook URL
     */
    public Webhook(String url) {
        this.url = url;
    }

    public Webhook withDefaults(String defaultUsername, String defaultAvatarUrl) {
        this.defaultUsername = defaultUsername;
        this.defaultAvatarUrl = defaultAvatarUrl;
        return this;
    }

    public Message createMessage(boolean wait, String threadId, boolean withComponents) {
        return new Message(wait, threadId, withComponents, this).tap(message -> {
            if (!defaultUsername.isEmpty()) message.setUsername(defaultUsername);
            if (!defaultAvatarUrl.isEmpty()) message.setAvatarUrl(defaultAvatarUrl);
        });
    }

    public Message createMessage() {
        return createMessage(false, null, false);
    }

    /**
     * Sends the given message asynchronously.
     *
     * @return a {@link CompletableFuture} that resolves to a {@link WebhookResponse}
     */
    public CompletableFuture<WebhookResponse> send(Message message) {
        try {
            String targetUrl = buildUrl(message);
            System.out.println(targetUrl);
            Map<String, Object> data = message.write();
            boolean hasFiles = !message.getFiles().isEmpty();

            HttpRequest request;
            if (hasFiles) {
                request = buildMultipartRequest(targetUrl, data, message);
            } else {
                request = buildJsonRequest(targetUrl, data);
            }

            return HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> new WebhookResponse(response.body(), response.statusCode()));

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private String buildUrl(Message message) {
        StringJoiner params = new StringJoiner("&");
        if (message.isWait()) params.add("wait=true");
        if (message.getThreadId() != null) params.add("thread_id=" + message.getThreadId());
        if (message.isWithComponents()) params.add("with_components=true");
        return params.length() == 0 ? url : url + "?" + params;
    }

    private HttpRequest buildJsonRequest(String targetUrl, Map<String, Object> data) {
        // Strip the internal "files" key before serializing (no files in JSON path)
        data.remove("files");

        String json = OBJECT_MAPPER.writeValueAsString(data);
        System.out.println(json);
        return HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    private HttpRequest buildMultipartRequest(String targetUrl, Map<String, Object> data, Message message) {
        // Build payload_json (everything except the files map itself)
        Map<String, Object> payloadData = new java.util.LinkedHashMap<>(data);
        payloadData.remove("files");
        String payloadJson = OBJECT_MAPPER.writeValueAsString(payloadData);

        MultipartBodyPublisher multipart = MultipartBodyPublisher.create();
        multipart.addPart("payload_json", payloadJson);

        for (Map.Entry<Integer, String[]> entry : message.getFiles().entrySet()) {
            String[] fileData = entry.getValue();
            // fileData: [filePath, mimeType, postedFileName]
            multipart.addFilePart("files[" + entry.getKey() + "]", fileData[0], fileData[1], fileData[2]);
        }

        return HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", multipart.getContentType())
                .POST(multipart.build())
                .build();
    }

    public static Webhook create(String url) {
        return new Webhook(url);
    }
}
