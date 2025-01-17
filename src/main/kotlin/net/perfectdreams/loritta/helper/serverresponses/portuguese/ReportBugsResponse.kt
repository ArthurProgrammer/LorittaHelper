package net.perfectdreams.loritta.helper.serverresponses.portuguese

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.perfectdreams.loritta.api.messages.LorittaReply
import net.perfectdreams.loritta.helper.serverresponses.RegExResponse
import java.util.regex.Pattern

/**
 * If you found a bug, you should report it!
 * This response teaches you how to do that on the finest way.
 */
class ReportBugsResponse : RegExResponse() {
    init {
        patterns.add("como|onde".toPattern(Pattern.CASE_INSENSITIVE))
        patterns.add("reporta|fala|falo".toPattern(Pattern.CASE_INSENSITIVE))
        patterns.add("bug|problema".toPattern(Pattern.CASE_INSENSITIVE))
    }

    override fun getResponse(event: GuildMessageReceivedEvent, message: String) =
        listOf(
            LorittaReply(
                "Você pode reportar bugs no nosso servidor de comunidade da Loritta! <#761625835043291146>",
                prefix = "🐛"
            )
        )
}