package commands.music

import com.lamergameryt.discordutils.commands.CommandEvent
import com.lamergameryt.discordutils.commands.SlashCommand
import dev.minn.jda.ktx.EmbedBuilder
import net.dv8tion.jda.api.Permission

class JoinCommand: SlashCommand() {
    init {
        this.guilds = arrayOf("855393545049473035", "544450528516767764")
        this.help = "Joins the same voice channel as yours!"
        this.name = "join"
    }

    override fun execute(event: CommandEvent) {
        val channel = event.guildChannel
        val guild = event.guild

        val user = event.member!!
        if(!event.guild!!.selfMember.hasPermission(channel, Permission.VOICE_CONNECT)) {
            event.reply("I do not have permissions to join a voice channel!").setEphemeral(true).queue();
            return;
        }
        val selfUser = guild!!.selfMember
        val selfVC = selfUser.voiceState
        if (selfVC!!.inVoiceChannel()){
            event.reply("Im already in a VC").setEphemeral(true).queue()
            return
        }
        val voiceState = user.voiceState
        voiceState ?: run {
            event.reply("No").queue()
            return
        }
        val channelvc = voiceState.channel
        channelvc ?: run {
            event.reply("You are not connected to any voice channel").setEphemeral(true).queue()
            return
        }

        val audioManager = guild.audioManager
        audioManager.openAudioConnection(channelvc)

        val embed = EmbedBuilder {
            description = "Connected to ${channelvc.asMention}"
        }

        event.replyEmbeds(embed.build()).queue()
    }
}