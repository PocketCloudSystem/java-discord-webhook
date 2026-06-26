package de.r3pt1s.discord.webhook.message.mention;

import de.r3pt1s.discord.webhook.util.WebhookHelper;
import de.r3pt1s.discord.webhook.util.Writeable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class AllowedMention implements Writeable {

    public static final int MAX_ROLES = 100;
    public static final int MAX_USERS = 100;

    private final List<String> allowedMentions = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
    private List<String> users = new ArrayList<>();

    public AllowedMention allow(AllowedMentionType type) {
        allowedMentions.add(type.getValue());
        return this;
    }

    public AllowedMention mentionedRoles(String... roleIds) {
        List<String> list = Arrays.asList(roleIds);
        if (list.size() > MAX_ROLES) list = list.subList(0, MAX_ROLES);
        this.roles = new ArrayList<>(list);
        return this;
    }

    public AllowedMention mentionedUsers(String... userIds) {
        List<String> list = Arrays.asList(userIds);
        if (list.size() > MAX_USERS) list = list.subList(0, MAX_USERS);
        this.users = new ArrayList<>(list);
        return this;
    }

    @Override
    public Map<String, Object> write() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("parse", allowedMentions);
        map.put("roles", roles);
        map.put("users", users);
        return WebhookHelper.removeNullFields(map);
    }

    public static AllowedMention create() {
        return new AllowedMention();
    }
}
