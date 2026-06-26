package de.r3pt1s.discord.webhook.message.mention;

public enum AllowedMentionType {

    ROLES("roles"),
    USERS("users"),
    EVERYONE("everyone");

    private final String value;

    AllowedMentionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
