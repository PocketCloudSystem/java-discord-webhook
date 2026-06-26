package de.r3pt1s.discord.webhook.poll;

import lombok.Getter;

@Getter
public enum PollLayoutType {

    DEFAULT(1);

    private final int value;

    PollLayoutType(int value) {
        this.value = value;
    }
}
