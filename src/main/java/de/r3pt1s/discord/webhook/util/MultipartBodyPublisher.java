package de.r3pt1s.discord.webhook.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MultipartBodyPublisher {

    private final String boundary = UUID.randomUUID().toString().replace("-", "");
    private final List<byte[]> parts = new ArrayList<>();

    private MultipartBodyPublisher() {}

    public MultipartBodyPublisher addPart(String name, String value) {
        String header = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n";
        parts.add(header.getBytes(StandardCharsets.UTF_8));
        parts.add((value + "\r\n").getBytes(StandardCharsets.UTF_8));
        return this;
    }

    public MultipartBodyPublisher addFilePart(String name, String filePath, String mimeType, String fileName) {
        try {
            byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
            String header = "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\n"
                    + "Content-Type: " + mimeType + "\r\n\r\n";
            parts.add(header.getBytes(StandardCharsets.UTF_8));
            parts.add(fileBytes);
            parts.add("\r\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return this;
    }

    public String getContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    public HttpRequest.BodyPublisher build() {
        byte[] closing = ("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);

        int totalLength = parts.stream().mapToInt(b -> b.length).sum() + closing.length;
        byte[] body = new byte[totalLength];
        int offset = 0;
        for (byte[] part : parts) {
            System.arraycopy(part, 0, body, offset, part.length);
            offset += part.length;
        }
        System.arraycopy(closing, 0, body, offset, closing.length);

        return HttpRequest.BodyPublishers.ofByteArray(body);
    }

    public static MultipartBodyPublisher create() {
        return new MultipartBodyPublisher();
    }
}
