package commands.utilities

import com.lamergameryt.discordutils.commands.CommandEvent
import com.lamergameryt.discordutils.commands.SlashCommand
import com.lamergameryt.discordutils.commands.annotations.CommandMarker
import com.lamergameryt.discordutils.commons.EventWaiter
import dev.minn.jda.ktx.EmbedBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import java.util.concurrent.TimeUnit
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class EvalCommand: SlashCommand() {
    init {
        this.guilds = arrayOf("855393545049473035", "544450528516767764")
        this.help = "Eval command"
        this.name = "eval"
        this.restrictedUsers = arrayOf("423008899302424596")
        this.options.add(OptionData(OptionType.STRING, "toeval", "Code to eval", false))
    }
    override fun execute(event: CommandEvent) {
        event.deferReply(true).queue()
        val arg = event.getOption("toeval")?.asString
//        val se = ScriptEngineManager().getEngineByExtension("kts") as KotlinJsr223JvmLocalScriptEngine
        val se = ScriptEngineManager().getEngineByName("Nashorn")
        try {
            se.eval("var imports = new JavaImporter(" +
                    "java.io," +
                    "java.lang," +
                    "java.util," +
                    "Packages.com.mashape.unirest.http," +
                    "Packages.net.dv8tion.jda.api," +
                    "Packages.net.dv8tion.jda.api.entities," +
                    "Packages.net.dv8tion.jda.api.entities.impl," +
                    "Packages.net.dv8tion.jda.api.managers," +
                    "Packages.net.dv8tion.jda.api.managers.impl," +
                    "Packages.net.dv8tion.jda.api.utils);")
            se.eval("var Unirest = imports.Unirest;")
        } catch (ignored: ScriptException) {
        }
        se.put("event", event)
        se.put("jda", event.jda)

        var toEval = ""
        if (arg.isNullOrEmpty() || arg.isNullOrBlank()){
            event.hook.editOriginal("Enter the code you want to eval").queue()
            EventWaiter.of(GuildMessageReceivedEvent::class.java)
                .withCondition { e ->
                    e.author.id == event.user.id
                }
                .setTimeout(30, TimeUnit.SECONDS)
                .onTimeout{ event.hook.editOriginal("Eval failed").queue() }
                .onAction{ e->
                    toEval = e.message.contentRaw
                }.submit()
        }
        else toEval = arg.toString()
        try {
            val embed = EmbedBuilder {
                title = "Evaluated Successfully"
                description="```\n${se.eval(toEval)}```".trimIndent()
                color = 0xff4444
            }
            event.hook.editOriginalEmbeds(embed.build()).queue()
        } catch (e: Exception) {
            event.hook.editOriginal("An exception was thrown:\n```\n$e```").queue()
        }
    }
}