package net.perfectdreams.loritta.helper.serverresponses.portuguese

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.perfectdreams.loritta.api.messages.LorittaReply
import net.perfectdreams.loritta.helper.serverresponses.RegExResponse
import net.perfectdreams.loritta.helper.utils.Emotes
import java.util.regex.Pattern

class ReceiveSonhosResponse : RegExResponse() {
    init {
        patterns.add("conseg|peg|ganh".toPattern(Pattern.CASE_INSENSITIVE))
        patterns.add("sonhos".toPattern(Pattern.CASE_INSENSITIVE))
    }

    override fun getResponse(event: GuildMessageReceivedEvent, message: String) =
        listOf(
            LorittaReply(
                "**Você pode conseguir sonhos... dormindo!**",
                Emotes.LORI_PAC
            ),
            LorittaReply(
                "Brincadeirinha!! ^-^ Você pode pegar sonhos usando `+daily`",
                prefix = Emotes.LORI_OWO,
                mentionUser = false
            ),
        )
}