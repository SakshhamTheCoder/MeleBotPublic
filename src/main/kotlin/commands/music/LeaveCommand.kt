package commands.music

import com.lamergameryt.discordutils.commands.CommandEvent
import com.lamergameryt.discordutils.commands.SlashCommand
import dev.minn.jda.ktx.EmbedBuilder
import net.dv8tion.jda.api.Permission

class LeaveCommand: SlashCommand() {
    init {
        this.guilds = arrayOf("855393545049473035", "544450528516767764")
        this.help = "Leaves the current voice channel"
        this.name = "leave"
    }

    override fun execute(event: CommandEvent) {
        val channel = event.guildChannel
        val user = event.member!!
        if(!event.guild!!.selfMember.hasPermission(channel, Permission.VOICE_CONNECT)) {
            event.reply("I do not have permissions to join a voice channel!").queue()
            return
        }
        val selfUser = event.guild!!.selfMember
        val selfVC = selfUser.voiceState
        if (!selfVC!!.inVoiceChannel()){
            event.reply("Im not in a VC. If u want me to join your VC, just use /join command").queue()
            return
        }
        val voiceState = user.voiceState
        voiceState ?: run {
            event.reply("No").queue()
            return
        }
        val userChannel = voiceState.channel
        if (selfVC.channel!! != userChannel){
            event.reply("You need to be connected to the same voice channel as me to use this command").queue()
            return
        }

        val audioManager = event.guild!!.audioManager
        audioManager.closeAudioConnection()

        val embed = EmbedBuilder {
            description = "Disconnected from ${selfVC.channel!!.asMention}"
        }

        event.replyEmbeds(embed.build()).queue()
    }
}