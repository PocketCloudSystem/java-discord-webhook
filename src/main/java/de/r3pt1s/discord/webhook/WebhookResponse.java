package de.r3pt1s.discord.webhook;

public record WebhookResponse(String body, int statusCode) {

    public boolean isSuccess() {
        int code = statusCode / 100;
        return code == 2;
    }
}
