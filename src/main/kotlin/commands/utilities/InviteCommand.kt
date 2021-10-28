package commands.utilities

import com.lamergameryt.discordutils.commands.CommandEvent
import com.lamergameryt.discordutils.commands.SlashCommand
import com.lamergameryt.discordutils.commands.annotations.Cooldown
import dev.minn.jda.ktx.EmbedBuilder
import jda
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.interactions.components.Button
import java.time.Instant
import kotlin.random.Random

@Cooldown(duration = 10)
class InviteCommand: SlashCommand() {
    init {
        this.help = "Invite this bot to your server"
        this.name = "invite"
        cooldown
    }
    override fun execute(event: CommandEvent) {
        val embed = EmbedBuilder {
            title = "Invite ${jda.selfUser.asMention}"
            description = "Here is the link of bot [Invite To Server](https://discord.com/oauth2/authorize?client_id=${jda.selfUser.id}&permissions=8589934591&scope=bot%20applications.commands)"
            color = Random.nextInt(0xffffff + 1)
            timestamp = Instant.now()
        }
        val actionrow = Button.link("https://discord.com/oauth2/authorize?client_id=${jda.selfUser.id}&permissions=8589934591&scope=bot%20applications.commands", Emoji.fromUnicode("➕"))
        event.replyEmbeds(embed.build()).addActionRow(actionrow).queue()
    }
}