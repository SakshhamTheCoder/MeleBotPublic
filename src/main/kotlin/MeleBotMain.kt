import com.lamergameryt.discordutils.commands.CommandClientBuilder
import com.lamergameryt.discordutils.commons.EventWaiter
import commands.utilities.RPSCommand
import dev.minn.jda.ktx.injectKTX
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.apache.log4j.BasicConfigurator
import java.time.Instant
import java.util.*

lateinit var jda: JDA
class MeleBotMain {
    fun bot(){
        BasicConfigurator.configure()
        val builder = CommandClientBuilder()
        builder.setActivity(Activity.competing("making rewrite for bot"))
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB)

        builder.addCommandPackage("commands")

        jda = JDABuilder.createDefault("token").enableIntents(
                GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES)
            .setChunkingFilter(ChunkingFilter.ALL)
            .enableCache(CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .addEventListeners(builder.build(), EventWaiter())
            .injectKTX()
            .build()
        jda.awaitReady()
        println(builder.build().commands)

        fun onReady(e: ReadyEvent) {
            val server = "  ____   ____ _______   _____  _____   _____  ______          _______     __\n" +
                    " |  _ \\ / __ \\__   __| |_   _|/ ____| |  __ \\|  ____|   /\\   |  __ \\ \\   / /\n" +
                    " | |_) | |  | | | |      | | | (___   | |__) | |__     /  \\  | |  | \\ \\_/ / \n" +
                    " |  _ <| |  | | | |      | |  \\___ \\  |  _  /|  __|   / /\\ \\ | |  | |\\   /  \n" +
                    " | |_) | |__| | | |     _| |_ ____) | | | \\ \\| |____ / ____ \\| |__| | | |   \n" +
                    " |____/ \\____/  |_|    |_____|_____/  |_|  \\_\\______/_/    \\_\\_____/  |_|   \n" +
                    "                                                                            \n" +
                    "                                                                            "
            println(server)
            val x = jda.getTextChannelById("865646750206001172")
//            val time = Calendar.getInstance().time.toString("\"dd/MM/yyyy HH:mm:ss\"")
            val y = EmbedBuilder();y.setColor(0xff4444);y.setTitle("BOT IS ONLINE");y.setDescription("Ready on ${Instant.now()}");y.setFooter("SabreBOT on JDA")
            val z = "---------------------------------------------------------------------------------"
            if (x!!.canTalk()) {
                x.sendMessage(z).embed(y.build()).queue()
            }
            else{
                println("F in chat")
            }
        }
        onReady(ReadyEvent(jda, 200))
    }
}
fun main(){
    MeleBotMain().bot()
}