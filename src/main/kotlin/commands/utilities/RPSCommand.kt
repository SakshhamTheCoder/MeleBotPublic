package commands.utilities

import com.lamergameryt.discordutils.commands.CommandEvent
import com.lamergameryt.discordutils.commands.SlashCommand
import com.lamergameryt.discordutils.commons.EventWaiter
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.Button
import java.util.concurrent.TimeUnit


class RPSCommand: SlashCommand() {
    init {
        this.guilds = arrayOf("855393545049473035", "544450528516767764")
        this.help = "RPS command"
        this.name = "rps"
    }
    override fun execute(event: CommandEvent) {
        event.reply("HLO").addActionRow(Button.primary("xyz", "Hello")).queue()
        EventWaiter.of(ButtonClickEvent::class.java)
            .withCondition { e ->
                e.button!!.id.equals("xyz")
                e.user.id == event.user.id
            }
            .setTimeout(10, TimeUnit.SECONDS)
            .onTimeout {
                event.textChannel
                    .sendMessage(event.user.asMention + " you failed to click the button within 10 seconds.").queue()
            }
            .onAction { e ->
                e.reply("GGWP").queue()
            }
            .submit()
    }
}