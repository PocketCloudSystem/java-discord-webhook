import de.r3pt1s.discord.webhook.Webhook;
import de.r3pt1s.discord.webhook.WebhookResponse;
import de.r3pt1s.discord.webhook.emoji.PartialEmoji;
import de.r3pt1s.discord.webhook.message.Message;
import de.r3pt1s.discord.webhook.message.component.impl.ActionRowComponent;
import de.r3pt1s.discord.webhook.message.component.impl.ButtonComponent;
import de.r3pt1s.discord.webhook.message.embed.Embed;
import de.r3pt1s.discord.webhook.poll.Poll;
import de.r3pt1s.discord.webhook.poll.PollLayoutType;

public final class WebhookTest {

    public static void main(String[] args) {
        System.out.println("hi");
        String url = "your url";
        Webhook webhook = Webhook.create(url).withDefaults("logs", "https://avatars.githubusercontent.com/u/81467242?v=4");
        Message message = webhook.createMessage();
        message.withComponents()
                .addComponent(ActionRowComponent.with(ButtonComponent.link("https://github.com/PocketCloudSystem", "GitHub Repo", null, false)))
                .setPoll(Poll.create("How was your day?", (System.currentTimeMillis() / 1000L) + (60 * 60), true, PollLayoutType.DEFAULT)
                        .addAnswer("Wonderful!", PartialEmoji.fromUnicode("\uD83D\uDC96"))
                        .addAnswer("Bad!", PartialEmoji.fromUnicode("\uD83D\uDE2D"))
                )
                .addEmbed(Embed.create()
                        .setAuthor("triple t", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR85JxjcEZOrCm1wlj4YHZSDgMIQ4l4GurGz8VkhAQEaQ&s=10", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR71WoV_KEKgm9MwJ8bkjfF5EdXrEig1Z929qanHDQdjg&s=10", null)
                        .addField("Field 1", "Hi", false)
                        .setColorRgb(250, 0, 0)
                        .setFooter("son im crine", null, null)
                        .setTimestampNow()
                );

        /**
         *                 .addComponent(ActionRowComponent.with(
         *                         ButtonComponent.link("https://github.com/PocketCloudSystem/", "GitHub Orga", PartialEmoji.fromUnicode("\uD83E\uDD7A"), false),
         *                         ButtonComponent.primary("test1", "Yo!", null, false),
         *                         ButtonComponent.secondary("test2", "Whats good!", null, true),
         *                         ButtonComponent.danger("test3", "Whoosh~~", PartialEmoji.fromUnicode("\uD83D\uDE48"), false),
         *                         ButtonComponent.success("test4", "Success!1!!", PartialEmoji.fromUnicode("✅"), true)
         *                 ))
         *                 .addEmbed(Embed.create()
         *                         .setAuthor("triple t", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR85JxjcEZOrCm1wlj4YHZSDgMIQ4l4GurGz8VkhAQEaQ&s=10", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR71WoV_KEKgm9MwJ8bkjfF5EdXrEig1Z929qanHDQdjg&s=10", null)
         *                         .addField("Field 1", "Hi", false)
         *                         .setColorRgb(250, 0, 0)
         *                         .setFooter("son im crine", null, null)
         *                         .setTimestampNow()
         *                 )
         *                 .setPoll(Poll.create("How was your day?", (System.currentTimeMillis() / 1000L) + (60 * 60), true, PollLayoutType.DEFAULT)
         *                         .addAnswer("Wonderful!", PartialEmoji.fromUnicode("\uD83D\uDC96"))
         *                         .addAnswer("Bad!", PartialEmoji.fromUnicode("\uD83D\uDE2D"))
         *                 );
         */

        WebhookResponse res = message.send().join();
        System.out.println("Success: " + res.isSuccess());
        System.out.println("StatusCode: " + res.statusCode());
        System.out.println("Body: " + res.body());
    }
}